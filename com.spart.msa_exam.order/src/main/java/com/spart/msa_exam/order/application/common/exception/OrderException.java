package com.spart.msa_exam.order.application.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public OrderException(OrderErrorCode orderErrorCode) {
        this.httpStatus = orderErrorCode.getHttpStatus();
        this.message = orderErrorCode.getMessage();
    }
}
