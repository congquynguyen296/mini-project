package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.request.AuthenticationRequest;
import com.accessed.miniproject.dto.request.LogoutRequest;
import com.accessed.miniproject.dto.request.RefreshTokenRequest;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.AuthenticationResponse;
import com.accessed.miniproject.dto.response.LogoutResponse;
import com.accessed.miniproject.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Authentication successful")
                .result(authenticationService.authenticate(request))
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<LogoutResponse> logout(@RequestBody LogoutRequest request) throws ParseException {

        return ApiResponse.<LogoutResponse>builder()
                .code(200)
                .message("Authentication successful")
                .result(authenticationService.logout(request))
                .build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<AuthenticationResponse> refresh(@RequestBody RefreshTokenRequest request) throws ParseException {

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Authentication successful")
                .result(authenticationService.refreshToken(request))
                .build();
    }
}
