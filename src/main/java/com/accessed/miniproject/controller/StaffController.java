package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.services.StaffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/staff")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StaffController {

    StaffService staffService;

    @GetMapping("/count")
    ApiResponse<Long> countStaffByCity(@RequestParam String city) {
        return ApiResponse.<Long>builder()
                .code(200)
                .message("API send success")
                .result(staffService.count(city))
                .build();
    }
    @GetMapping("/popular")
    public ApiResponse<?> numberOfPopularStaffsByCity(@RequestParam String city){
        return ApiResponse.builder()
                .code(200)
                .message("Successful API")
                .result(staffService.getPopularStaffs(city))
                .build();
    }

}
