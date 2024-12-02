package com.spart.msa_exam.product.application.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode {

    DUPLICATE_PRODUCT_NAME(HttpStatus.CONFLICT, "상품 이름이 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
