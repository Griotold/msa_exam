package com.spart.msa_exam.auth.application.usecase.command;

public record SignUpCommand(
        String userId,
        String username,
        String password,
        String role)
{
}
