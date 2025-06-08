package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.PopularLocationResponse;
import com.accessed.miniproject.dto.response.PopularServiceResponse;
import com.accessed.miniproject.services.LocationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationController {
    LocationService locationService;

    @GetMapping
    public ApiResponse<HashMap<String, String>> numberOfLocationsByCity(@RequestParam String city){
        HashMap<String, String> result = new HashMap<>();
        result.put("locationCount", locationService.numberOfLocationsByCity(city) + "");
        return ApiResponse.<HashMap<String, String>>builder()
                .code(200)
                .message("Successful API")
                .result(result)
                .build();
    }
    @GetMapping("/popular")
    public ApiResponse<?> numberOfPopularLocationsByCity(@RequestParam String city){
        return ApiResponse.builder()
                .code(200)
                .message("Successful API")
                .result(locationService.getPopularLocations(city))
                .build();
    }

}
