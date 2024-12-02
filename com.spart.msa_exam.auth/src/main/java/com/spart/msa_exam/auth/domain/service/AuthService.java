package com.spart.msa_exam.auth.domain.service;

import com.spart.msa_exam.auth.application.common.exception.AuthErrorCode;
import com.spart.msa_exam.auth.application.common.exception.AuthException;
import com.spart.msa_exam.auth.domain.entity.User;
import com.spart.msa_exam.auth.domain.enums.Role;
import com.spart.msa_exam.auth.domain.repository.UserRepository;
import com.spart.msa_exam.auth.domain.service.dto.SignUpResponse;
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
     * @param userId 사용자 ID
     * @return 저장된 사용자
     * @Param username 사용자명
     * @Param password 비밀번호
     * @Param role 권한
     */
    @Transactional
    public SignUpResponse signUp(String userId, String username, String password, String role) {
        // userId 중복 체크
        if (userRepository.findById(userId).isPresent()) {
            throw new AuthException(AuthErrorCode.DUPLICATE_USERNAME);
        }
        User user = User.create(userId, username,
                passwordEncoder.encode(password),
                Role.of(role));
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
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.createAccessToken(user.getUserId(), user.getRole());
    }
}