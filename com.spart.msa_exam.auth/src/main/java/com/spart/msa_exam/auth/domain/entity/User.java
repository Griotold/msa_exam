package com.spart.msa_exam.auth.domain.entity;

import com.spart.msa_exam.auth.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Table(name = "users")
@Entity
public class User {
    @Id
    private String userId;
    private String username;
    private String password;
    private Role role;

    public static User create(String userId, String username, String password, Role role) {
        return User.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }
}
