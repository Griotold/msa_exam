package com.spart.msa_exam.auth.presentation.request;

import com.spart.msa_exam.auth.application.usecase.command.SignInCommand;
import com.spart.msa_exam.auth.application.usecase.command.SignUpCommand;

public record SignInRequest(
        String userId,
        String password
) {
    public SignInCommand toCommand() {
        return new SignInCommand(userId, password);
    }
}
