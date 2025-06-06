package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.UserCreationResponse;
import com.accessed.miniproject.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @PostMapping("/create")
    ApiResponse<UserCreationResponse> createUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .code(200)
                .message("API send success")
                .result(userService.createUser(request))
                .build();
    }
}
