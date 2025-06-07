package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.request.ProvincesCreationRequest;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.ProvincesResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.model.Provinces;
import com.accessed.miniproject.services.ProvincesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/provinces")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ProvincesController {

    ProvincesService provincesService;

    @GetMapping("/image")
    public ApiResponse<String> findProvinceImageByCity(@RequestParam("city") String city) {

        if (city == null || city.isEmpty()) {
            throw new AppException(EErrorCode.NOT_FOUND);
        }

        return ApiResponse.<String>builder()
                .code(200)
                .message("API send success")
                .result(provincesService.findImageByCity(city))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<ProvincesResponse> createProvinces(@RequestBody ProvincesCreationRequest request) {

        return ApiResponse.<ProvincesResponse>builder()
                .code(200)
                .message("API send success")
                .result(provincesService.createProvinces(request))
                .build();
    }
}
