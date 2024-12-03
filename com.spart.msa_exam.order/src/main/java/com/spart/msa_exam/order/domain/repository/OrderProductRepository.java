package com.spart.msa_exam.order.domain.repository;

import com.spart.msa_exam.order.domain.entity.OrderProduct;

import java.util.Optional;

public interface OrderProductRepository {
    Optional<OrderProduct> findbyId(Long id);

    OrderProduct save(OrderProduct orderProduct);
}
