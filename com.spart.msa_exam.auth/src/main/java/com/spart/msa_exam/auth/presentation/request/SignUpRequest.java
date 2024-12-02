package com.spart.msa_exam.auth.presentation.request;

import com.spart.msa_exam.auth.application.usecase.command.SignUpCommand;

public record SignUpRequest(
        String userId,
        String username,
        String password,
        String role
) {
    public SignUpCommand toCommand() {
        return new SignUpCommand(userId, username, password, role);
    }
}
