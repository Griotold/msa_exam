package com.spart.msa_exam.order.application.common;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(HttpStatus status, String message, T data) {
    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }
}
