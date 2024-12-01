package com.spart.msa_exam.auth.application.dto;

public record SignInRequest(
        String userId,
        String password
) {
}
