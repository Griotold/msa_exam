package com.spart.msa_exam.order.domain.repository;

import com.spart.msa_exam.order.domain.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    Optional<Order> findById(Long id);

    Order save(Order order);
}
