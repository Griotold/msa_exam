package com.spart.msa_exam.auth.application.service;


import com.spart.msa_exam.auth.application.common.exception.AuthException;
import com.spart.msa_exam.auth.domain.service.dto.SignUpResponse;
import com.spart.msa_exam.auth.domain.entity.User;
import com.spart.msa_exam.auth.domain.repository.UserRepository;
import com.spart.msa_exam.auth.domain.service.AuthService;
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
        String userId = "testId";
        String username = "testName";
        String password = "password";
        String role = "customer";

        // when
        SignUpResponse response = authService.signUp(userId, username, password, role);

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
        String userId = "testId";
        String username = "testName";
        String password = "password";
        String role = "customer";

        authService.signUp(userId, username, password, role);

        // when
        String token = authService.signIn("testId", "password");

        // then
        assertThat(token).isNotNull();
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인을 시도하면 AuthException 예외가 발생한다.")
    void signInFailWithWrongPassword() {
        // given
        String userId = "testId";
        String username = "testName";
        String password = "password";
        String role = "customer";

        authService.signUp(userId, username, password, role);

        // when & then
        assertThatThrownBy(() -> authService.signIn("testId", "wrongPassword"))
                .isInstanceOf(AuthException.class)
                .hasMessage("유저 ID 또는 비밀번호 정보가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("존재하지 않는 사용자로 로그인을 시도하면 AuthException 예외가 발생한다.")
    void signInFailWithNonExistentUser() {
        // when & then
        assertThatThrownBy(() -> authService.signIn("nonexistent", "password"))
                .isInstanceOf(AuthException.class)
                .hasMessage("일치하는 유저 정보가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("이미 존재하는 userId로 회원가입 시도하면 AuthException 예외가 발생한다.")
    void signUpFailWithExistingUserId() {
        // given
        String existingId = "existingId";
        String username = "existingName";
        String password = "existingPassword";
        String role = "customer";

        authService.signUp(existingId, username, password, role);

        // when & then
        assertThatThrownBy(() ->
                authService.signUp(existingId, "anotherName", "anotherPassowrd", "customer"))
                .isInstanceOf(AuthException.class)
                .hasMessage("유저 이름이 이미 존재합니다.");
    }
}