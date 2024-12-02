package com.spart.msa_exam.order.infra.persistence;

import com.spart.msa_exam.order.domain.entity.Order;
import com.spart.msa_exam.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }
}
