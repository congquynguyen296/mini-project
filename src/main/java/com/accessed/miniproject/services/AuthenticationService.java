package com.accessed.miniproject.services;


import com.accessed.miniproject.constant.ErrorCode;
import com.accessed.miniproject.dto.request.IntrospectRequest;
import com.accessed.miniproject.dto.response.IntrospectResponse;
import com.accessed.miniproject.exception.AppException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    @NonFinal // Advice insert this into bean IoC
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    // Login

    // Logout


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

    // Generate token

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
                    // .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli())
                    : signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);

            if (!verified || !expTime.after(new Date()))
                throw new AppException(ErrorCode.UNAUTHORIZED);

            // Kiểm tra token đã logout chưa...

            return signedJWT;

        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

}
