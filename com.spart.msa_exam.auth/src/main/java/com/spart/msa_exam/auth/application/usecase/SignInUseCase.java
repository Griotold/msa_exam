package com.spart.msa_exam.auth.application.usecase;

import com.spart.msa_exam.auth.application.common.UseCase;
import com.spart.msa_exam.auth.application.dto.SignInRequest;
import com.spart.msa_exam.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class SignInUseCase {

    private final AuthService authService;

    public String execute(SignInRequest signInRequest) {
        log.info("SignInUseCase.signInRequest: " + signInRequest);
        return authService.signIn(signInRequest.userId(), signInRequest.password());
    }

}
