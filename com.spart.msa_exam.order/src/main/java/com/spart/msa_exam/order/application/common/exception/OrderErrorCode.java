package com.spart.msa_exam.order.application.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode {

    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다."),

    // FORBIDDEN
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "접근 권한이 존재하지 않습니다."),

    INVALID_PRODUCT_LIST(HttpStatus.NOT_FOUND, "상품 ID 리스트는 null이거나 비어 있을 수 없습니다."),

    // circuit breaker 용 실패 케이스
    ORDER_FAIL(HttpStatus.SERVICE_UNAVAILABLE, "잠시 후에 주문 추가를 요청 해주세요.");

    private final HttpStatus httpStatus;
    private final String message;
}
