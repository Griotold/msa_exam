package com.spart.msa_exam.auth.application.usecase;

import com.spart.msa_exam.auth.application.common.UseCase;
import com.spart.msa_exam.auth.application.usecase.command.SignInCommand;
import com.spart.msa_exam.auth.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class SignInUseCase {

    private final AuthService authService;

    public String execute(SignInCommand command) {
        log.info("SignInUseCase.SignInCommand: " + command);
        return authService.signIn(command.userId(), command.password());
    }

}
