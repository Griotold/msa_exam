package com.spart.msa_exam.auth.application.common.exception;

public record ExceptionResponse(
        String message
) {
    public String toWrite() {
        return "{" +
                "\"message\":" + "\"" + message +"\"" +
                "}";
    }
}
