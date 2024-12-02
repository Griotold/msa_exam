package com.spart.msa_exam.auth.application.usecase;

import com.spart.msa_exam.auth.application.usecase.command.SignUpCommand;
import com.spart.msa_exam.auth.domain.service.AuthService;
import com.spart.msa_exam.auth.domain.service.dto.SignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class SignUpUseCase {

    private final AuthService authService;

    public SignUpResponse execute(SignUpCommand command) {
        log.info("SignUpUseCase.SignUpCommand: " + command);
        return authService.signUp(command.userId(), command.username(), command.password(), command.role());
    }
}
