package com.spart.msa_exam.product.infra.persistence;

import com.spart.msa_exam.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
