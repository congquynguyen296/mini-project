package com.accessed.miniproject.controller;

import com.accessed.miniproject.config.JwtDecoderCustom;
import com.accessed.miniproject.dto.response.ApiResponse;
import com.accessed.miniproject.model.Favorite;
import com.accessed.miniproject.services.FavoriteService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/favorite")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FavoriteController {

    final JwtDecoderCustom jwtDecoder;
    final FavoriteService favoriteService;

    @PostMapping
    public ApiResponse<?> createFavorite(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, Object> subjectID) {
        String accessToken = authHeader.substring(7);
        Jwt jwt = jwtDecoder.decode(accessToken);
        String sub = jwt.getClaim("sub");

        String id = subjectID.get("subjectid").toString();

        favoriteService.createFavorite(sub, id);

        return ApiResponse.<String>builder()
                .code(200)
                .message("Successful API")
                .result("Thêm thành công")
                .build();
    }

    @PostMapping("/delete")
    public ApiResponse<?> deleteFavorite(@RequestBody Map<String, Object> subjectID) {

        String id = subjectID.get("subjectid").toString();

        favoriteService.deleteFavorite(id);

        return ApiResponse.<String>builder()
                .code(200)
                .message("Successful API")
                .result("Xóa thành công")
                .build();
    }

}
