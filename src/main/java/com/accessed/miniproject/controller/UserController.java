package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.request.UserCreationRequest;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.UserCreationResponse;
import com.accessed.miniproject.dto.response.UserResponse;
import com.accessed.miniproject.model.User;
import com.accessed.miniproject.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @PostMapping("/user/create")
    ApiResponse<UserCreationResponse> createUser(@RequestBody UserCreationRequest request) {
        return ApiResponse.<UserCreationResponse>builder()
                .code(200)
                .message("API send success")
                .result(userService.createUser(request))
                .build();
    }

    @GetMapping("/users")
    ApiResponse<List<UserResponse>> findUsersByCity(@RequestParam(value = "city") String city,
                                                    @RequestParam(defaultValue = "0") String page,
                                                    @RequestParam(defaultValue = "10") String size) {

        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("API send success")
                .result(userService.searchUsersByCity(city,
                        Integer.parseInt(page),
                        Integer.parseInt(size)))
                .build();
    }
}
