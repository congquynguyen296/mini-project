package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.PopularServiceResponse;
import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.exception.AppException;
import com.accessed.miniproject.services.ServiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/service")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ServiceController {

    ServiceService serviceService;

    @GetMapping("/popular")
    public ApiResponse<List<PopularServiceResponse>> findProvinceImageByCity(@RequestParam("city") String city) {

        if (city == null || city.isEmpty()) {
            throw new AppException(EErrorCode.NOT_FOUND);
        }

        return ApiResponse.<List<PopularServiceResponse>>builder()
                .code(200)
                .message("API send success")
                // .result(serviceService.findPopularServiceByCity(city))
                .result(serviceService.findPopularServiceByCityWithRedis(city))
                .build();
    }
}
