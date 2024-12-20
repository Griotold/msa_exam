package com.spart.msa_exam.auth.infra.config.jwt;

import com.spart.msa_exam.auth.domain.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @Value로 주입받는 값들은 ReflectionTestUtils를 사용해 직접 설정할 수 있습니다
 * */
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Encoders.BASE64URL.encode(secretKey.getEncoded());
        jwtTokenProvider = new JwtTokenProvider(encodedKey);
        ReflectionTestUtils.setField(jwtTokenProvider, "issuer", "auth-service");
        ReflectionTestUtils.setField(jwtTokenProvider, "accessExpiration", 3600000L);
    }

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