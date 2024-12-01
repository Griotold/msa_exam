package com.spart.msa_exam.auth.application.dto;

public record SignUpRequest(
        String userId,
        String username,
        String password,
        String role
) {
}
