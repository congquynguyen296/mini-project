package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.services.GeoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/geo")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class GeoController {
    GeoService geoService;

    @GetMapping("/city")
    public ApiResponse<String> getCity(@RequestParam double lat, @RequestParam double longitude) {
        System.out.println("Go city");
        return ApiResponse.<String>builder()
                .result(geoService.getCity(lat, longitude))
                .build();
    }
}
