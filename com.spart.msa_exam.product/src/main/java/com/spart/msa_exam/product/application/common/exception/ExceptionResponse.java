package com.spart.msa_exam.product.application.common.exception;

public record ExceptionResponse(
        String message
) {
    public String toWrite() {
        return "{" +
                "\"message\":" + "\"" + message + "\"" +
                "}";
    }
}
