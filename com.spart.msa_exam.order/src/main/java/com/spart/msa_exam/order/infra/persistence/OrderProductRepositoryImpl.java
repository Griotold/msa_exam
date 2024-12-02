package com.spart.msa_exam.order.infra.persistence;

import com.spart.msa_exam.order.domain.entity.OrderProduct;
import com.spart.msa_exam.order.domain.repository.OrderProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {
    private final OrderProductJpaRepository orderProductJpaRepository;

    @Override
    public Optional<OrderProduct> findbyId(Long id) {
        return orderProductJpaRepository.findById(id);
    }

    @Override
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductJpaRepository.save(orderProduct);
    }
}
