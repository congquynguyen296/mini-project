package com.accessed.miniproject.exception;

import com.accessed.miniproject.enums.EErrorCode;
import com.accessed.miniproject.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<String>> exceptionHandler(Exception e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(EErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(EErrorCode.UNCATEGORIZED.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<String>> appExceptionHandler(AppException e) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getErrorCode().getMessage());
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> accessDeniedExceptionHandler(AccessDeniedException e) {
        EErrorCode errorCode = EErrorCode.FORBIDDEN;

        return ResponseEntity.status(errorCode.getHttpStatusCode()).body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

}
