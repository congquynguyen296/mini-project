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

    NOT_FOUND(103, "This resource does not exist", HttpStatus.NOT_FOUND),
    NOT_SAVE(104, "This resource does not saved into database", HttpStatus.BAD_REQUEST),
    RESOURCE_EXISTED(105, "This resource is existed", HttpStatus.BAD_REQUEST),
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
