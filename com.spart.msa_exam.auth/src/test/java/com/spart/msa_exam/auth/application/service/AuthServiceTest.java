package com.spart.msa_exam.auth.application.service;


import com.spart.msa_exam.auth.application.dto.SignUpRequest;
import com.spart.msa_exam.auth.application.dto.SignUpResponse;
import com.spart.msa_exam.auth.domain.entity.User;
import com.spart.msa_exam.auth.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signUpSuccess() {
        // given
        SignUpRequest request = new SignUpRequest("testId", "testName", "password", "customer");

        // when
        SignUpResponse response = authService.signUp(request);

        // then
        assertThat(response.userId()).isEqualTo("testId");
        assertThat(response.username()).isEqualTo("testName");

        User savedUser = userRepository.findById("testId").orElseThrow();
        assertThat(passwordEncoder.matches("password", savedUser.getPassword())).isTrue();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void signInSuccess() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("testId", "testName", "password", "customer");
        authService.signUp(signUpRequest);

        // when
        String token = authService.signIn("testId", "password");

        // then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인 실패 테스트")
    void signInFailWithWrongPassword() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("testId", "testName", "password", "customer");
        authService.signUp(signUpRequest);

        // when & then
        assertThatThrownBy(() -> authService.signIn("testId", "wrongPassword"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid user ID or password");
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인 실패 테스트")
    void signInFailWithNonExistentUser() {
        // when & then
        assertThatThrownBy(() -> authService.signIn("nonexistent", "password"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid user ID or password");
    }
}