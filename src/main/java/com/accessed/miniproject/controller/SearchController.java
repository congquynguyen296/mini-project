package com.accessed.miniproject.controller;

import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.services.SearchService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchController {

    SearchService searchService;

    @GetMapping("/search")
    public ApiResponse<Map<String, Object>> searchForUser(@RequestParam("keyword") String keyword,
                                                          @RequestParam("city") String city) {

        return ApiResponse.<Map<String, Object>>builder()
                .code(200)
                .message("Send API success")
                .result(searchService.searchForUser(keyword, city))
                .build();
    }
}
