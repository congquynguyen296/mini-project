package com.accessed.miniproject.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum EErrorCode {
    UNCATEGORIZED(100, "Uncategorized exception", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(101, "Unauthorized exception", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(102, "Unauthorized exception", HttpStatus.FORBIDDEN),

    NOTFOUND(103, "This resource does not exist", HttpStatus.NOT_FOUND),
    ;

    EErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    int code;
    String message;
    HttpStatusCode httpStatusCode;
}
