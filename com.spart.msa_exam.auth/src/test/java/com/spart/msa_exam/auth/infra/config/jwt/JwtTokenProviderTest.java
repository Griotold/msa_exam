package com.spart.msa_exam.auth.infra.config.jwt;

import com.spart.msa_exam.auth.domain.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("JWT 토큰 생성 테스트")
    void createAccessTokenTest() {
        // given
        String userId = "testUser";
        Role role = Role.CUSTOMER;

        // when
        String token = jwtTokenProvider.createAccessToken(userId, role);

        // then
        assertThat(token).isNotNull();

        Claims claims = Jwts.parser()
                .verifyWith(jwtTokenProvider.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        assertThat(claims.get("user_id")).isEqualTo(userId);
        assertThat(claims.get("role")).isEqualTo(role.name());
        assertThat(claims.getIssuer()).isEqualTo("auth-service");
    }
}