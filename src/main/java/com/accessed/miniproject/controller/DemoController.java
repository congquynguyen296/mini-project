package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DemoController {

    @PostMapping("/hello")
    ApiResponse<String> hello() {
        System.out.println("OK");
        return ApiResponse.<String>builder()
                .code(200)
                .message("API send success")
                .result("Hello world")
                .build();
    }
}
