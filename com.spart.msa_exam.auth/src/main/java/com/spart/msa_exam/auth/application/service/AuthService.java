package com.spart.msa_exam.auth.application.service;

import com.spart.msa_exam.auth.application.dto.SignUpRequest;
import com.spart.msa_exam.auth.application.dto.SignUpResponse;
import com.spart.msa_exam.auth.domain.entity.User;
import com.spart.msa_exam.auth.domain.enums.Role;
import com.spart.msa_exam.auth.domain.repository.UserRepository;
import com.spart.msa_exam.auth.infra.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 사용자 등록
     *
     * @param signUpRequest 사용자 정보
     * @return 저장된 사용자
     */
    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        User user = User.create(signUpRequest.userId(), signUpRequest.username(),
                passwordEncoder.encode(signUpRequest.password()),
                Role.of(signUpRequest.role()));
        return SignUpResponse.from(userRepository.save(user));
    }

    /**
     * 사용자 인증
     *
     * @param userId   사용자 ID
     * @param password 비밀번호
     * @return JWT 액세스 토큰
     */
    public String signIn(String userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid user ID or password");
        }

        return jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole());
    }
}