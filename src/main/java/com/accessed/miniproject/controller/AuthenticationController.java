package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.request.AuthenticationRequest;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.AuthenticationResponse;
import com.accessed.miniproject.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(AuthenticationRequest request) {

        return ApiResponse.<AuthenticationResponse>builder()
                .code(200)
                .message("Authentication successful")
                .result(authenticationService.authenticate(request))
                .build();
    }
}
