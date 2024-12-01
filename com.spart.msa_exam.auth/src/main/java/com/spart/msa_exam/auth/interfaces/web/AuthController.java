package com.spart.msa_exam.auth.interfaces.web;

import com.spart.msa_exam.auth.application.dto.SignInRequest;
import com.spart.msa_exam.auth.application.dto.SignUpRequest;
import com.spart.msa_exam.auth.application.dto.SignUpResponse;
import com.spart.msa_exam.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @Value("${server.port}")
    private int port;

    @PostMapping("/auth/sign-in")
    public ApiResponse<String> createAuthenticationToken(@RequestBody SignInRequest signInRequest) {
        String token = authService.signIn(signInRequest.userId(), signInRequest.password());
        return ApiResponse.success(HttpStatus.OK, "login success", token);
    }

    @PostMapping("/auth/sign-up")
    public ApiResponse<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);
        return ApiResponse.success(HttpStatus.OK, "sign up success", signUpResponse);
    }
}
