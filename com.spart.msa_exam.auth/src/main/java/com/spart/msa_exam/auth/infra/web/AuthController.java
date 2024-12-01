package com.spart.msa_exam.auth.infra.web;

import com.spart.msa_exam.auth.application.dto.SignInRequest;
import com.spart.msa_exam.auth.application.dto.SignInResponse;
import com.spart.msa_exam.auth.application.dto.SignUpRequest;
import com.spart.msa_exam.auth.application.dto.SignUpResponse;
import com.spart.msa_exam.auth.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/sign-in")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequest signInRequest) {
        String token = authService.signIn(signInRequest.userId(), signInRequest.password());
        return ResponseEntity.ok(new SignInResponse(token));
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }
}
