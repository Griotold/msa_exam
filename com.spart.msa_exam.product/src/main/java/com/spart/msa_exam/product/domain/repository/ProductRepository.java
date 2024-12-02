package com.spart.msa_exam.product.domain.repository;

import com.spart.msa_exam.product.domain.entity.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    Product save(Product product);

    Optional<Product> findByName(String name);
}
