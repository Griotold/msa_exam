package com.spart.msa_exam.auth.application.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class AuthException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public AuthException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}
