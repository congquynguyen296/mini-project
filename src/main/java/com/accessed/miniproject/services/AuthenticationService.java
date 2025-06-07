package com.accessed.miniproject.services;


import com.accessed.miniproject.dto.request.AuthenticationRequest;
import com.accessed.miniproject.dto.request.IntrospectRequest;
import com.accessed.miniproject.dto.request.LogoutRequest;
import com.accessed.miniproject.dto.request.RefreshTokenRequest;
import com.accessed.miniproject.dto.response.AuthenticationResponse;
import com.accessed.miniproject.dto.response.IntrospectResponse;
import com.accessed.miniproject.dto.response.LogoutResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.model.TokenValidation;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.repositories.TokenValidationRepository;
import com.accessed.miniproject.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    @NonFinal
    @Value("${jwt.signer-key}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    TokenValidationRepository tokenValidationRepository;

    // Login
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(EErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new AppException(EErrorCode.UNAUTHORIZED);

        // Handle other

        // Generate token if authenticated is success
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    // Logout
    public LogoutResponse logout(LogoutRequest request) throws ParseException {

        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

        TokenValidation token = TokenValidation.builder()
                .id(jit)
                .expiryDate(expiration)
                .build();

        try {
            tokenValidationRepository.save(token);
            return LogoutResponse.builder()
                    .expiryDate(expiration)
                    .token(request.getToken())
                    .build();

        } catch (RuntimeException e) {
            throw new AppException(EErrorCode.NOT_SAVE);
        }
    }


    // Method introspect (verify token)
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) {
        String token = introspectRequest.getToken();
        var isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    // Refresh token
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException {

        SignedJWT signedJWT = verifyToken(request.getToken(), true);

        // Build and save recent token (will be invalid)
        String jit = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
        TokenValidation recentToken = TokenValidation.builder()
                .id(jit)
                .expiryDate(expiration)
                .build();

        try {
            tokenValidationRepository.save(recentToken);
        } catch (RuntimeException e) {
            throw new AppException(EErrorCode.NOT_SAVE);
        }

        // Update new token
        String username = signedJWT.getJWTClaimsSet().getSubject();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(EErrorCode.NOT_FOUND));

        String newToken = generateToken(user);
        return AuthenticationResponse.builder()
                .token(newToken)
                .build();
    }

    // Generate token
    private String generateToken(User user) {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("system")
                .issueTime(new Date())
                .claim("role", "CUSTOMER")
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    // Get info and verify token
    private SignedJWT verifyToken(String token, boolean isRefresh) {
        try {
            JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(token);

            Date expTime = (isRefresh)
                    ? new Date(signedJWT
                    .getJWTClaimsSet()
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);

            if (!verified || !expTime.after(new Date()))
                throw new AppException(EErrorCode.UNAUTHORIZED);

            // Check token logout
            String jit = signedJWT.getJWTClaimsSet().getJWTID();
            boolean isExisted = tokenValidationRepository.existsById(jit);
            if (isExisted) throw new AppException(EErrorCode.RESOURCE_EXISTED);

            return signedJWT;

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
