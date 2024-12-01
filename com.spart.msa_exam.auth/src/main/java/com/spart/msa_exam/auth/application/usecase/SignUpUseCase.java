package com.spart.msa_exam.auth.application.usecase;

import com.spart.msa_exam.auth.application.common.UseCase;
import com.spart.msa_exam.auth.application.dto.SignUpRequest;
import com.spart.msa_exam.auth.application.dto.SignUpResponse;
import com.spart.msa_exam.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class SignUpUseCase {

    private final AuthService authService;

    public SignUpResponse execute(SignUpRequest signUpRequest) {
        log.info("SignUpUseCase.sigunUpRequest: " + signUpRequest);
        return authService.signUp(signUpRequest);
    }
}
