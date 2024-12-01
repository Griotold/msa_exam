package com.spart.msa_exam.auth.infra.persistence;

import com.spart.msa_exam.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, String> {
}
