package com.spart.msa_exam.auth.application.usecase.command;

public record SignInCommand(
        String userId,
        String password
) {
}
