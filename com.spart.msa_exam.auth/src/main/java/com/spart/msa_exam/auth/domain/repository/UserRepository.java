package com.spart.msa_exam.auth.domain.repository;

import com.spart.msa_exam.auth.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(String userId);

    User save(User user);
}
