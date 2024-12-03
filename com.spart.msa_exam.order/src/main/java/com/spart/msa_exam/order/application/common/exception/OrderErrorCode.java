package com.spart.msa_exam.order.application.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

    // FORBIDDEN
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 존재하지 않습니다."),

    INVALID_PRODUCT_LIST(HttpStatus.NOT_FOUND, "상품 ID 리스트는 null이거나 비어 있을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
