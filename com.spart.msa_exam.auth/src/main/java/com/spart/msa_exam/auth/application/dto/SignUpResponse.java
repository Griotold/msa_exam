package com.spart.msa_exam.auth.application.dto;

import com.spart.msa_exam.auth.domain.entity.User;

public record SignUpResponse(
        String userId,
        String username,
        String password,
        String role
) {

    public static SignUpResponse from(User user) {
        return new SignUpResponse(user.getUserId(), user.getUsername(), user.getPassword(), user.getRole().toString());
    }
}
