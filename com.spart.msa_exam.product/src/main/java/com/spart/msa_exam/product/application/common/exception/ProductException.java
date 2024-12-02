package com.spart.msa_exam.product.application.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProductException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public ProductException(ProductErrorCode productErrorCode) {
        this.httpStatus = productErrorCode.getHttpStatus();
        this.message = productErrorCode.getMessage();
    }
}
