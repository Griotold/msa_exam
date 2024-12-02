package com.spart.msa_exam.auth.presentation.controller;

import com.spart.msa_exam.auth.application.common.ApiResponse;
import com.spart.msa_exam.auth.application.usecase.SignInUseCase;
import com.spart.msa_exam.auth.application.usecase.SignUpUseCase;
import com.spart.msa_exam.auth.domain.service.dto.SignUpResponse;
import com.spart.msa_exam.auth.presentation.request.SignInRequest;
import com.spart.msa_exam.auth.presentation.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final SignInUseCase signInUseCase;
    private final SignUpUseCase signUpUseCase;

    @PostMapping("/auth/sign-in")
    public ApiResponse<String> signIn(@RequestBody SignInRequest signInRequest) {
        String token = signInUseCase.execute(signInRequest.toCommand());
        return ApiResponse.success(HttpStatus.OK, "login success", token);
    }

    @PostMapping("/auth/sign-up")
    public ApiResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = signUpUseCase.execute(signUpRequest.toCommand());
        return ApiResponse.success(HttpStatus.OK, "sign up success", signUpResponse);
    }
}
