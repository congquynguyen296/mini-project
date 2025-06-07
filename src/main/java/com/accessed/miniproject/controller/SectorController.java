package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.dto.response.PageResponse;
import com.accessed.miniproject.model.Sector;
import com.accessed.miniproject.services.SectorService;
import com.accessed.miniproject.services.StaffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/sector")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SectorController {

    SectorService sectorService;

    @GetMapping("/all")
    public ApiResponse<PageResponse<Sector>> getAllSectors(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size) {
        return ApiResponse.<PageResponse<Sector>>builder()
                .result(sectorService.getAllSectors(page, size))
                .build();
    }
}
