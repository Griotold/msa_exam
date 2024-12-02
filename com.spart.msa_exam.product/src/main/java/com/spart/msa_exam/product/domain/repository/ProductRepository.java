package com.spart.msa_exam.product.domain.repository;

import com.spart.msa_exam.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);

    Product save(Product product);

    Optional<Product> findByName(String name);

    Page<Product> findAll(String name, Pageable pageable);
}
