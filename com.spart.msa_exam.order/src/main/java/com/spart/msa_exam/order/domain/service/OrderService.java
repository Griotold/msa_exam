package com.spart.msa_exam.order.domain.service;

import com.spart.msa_exam.order.domain.entity.Order;
import com.spart.msa_exam.order.domain.entity.OrderProduct;
import com.spart.msa_exam.order.domain.repository.OrderRepository;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderCreateResponse create(List<Long> productIds) {
        log.info("Creating order with product IDs: {}", productIds);
        if (productIds == null || productIds.isEmpty()) {
            throw new IllegalArgumentException("상품 ID 리스트는 null이거나 비어있을 수 없습니다.");
        }
        Order order = Order.craete(new ArrayList<>());

        List<OrderProduct> orderProducts = productIds.stream()
                .map(productId -> OrderProduct.create(order, productId))
                .collect(Collectors.toList());

        order.addOrderProducts(orderProducts);

        Order savedOrder = orderRepository.save(order);

        log.info("Order created successfully with ID: {}", savedOrder.getId());

        return OrderCreateResponse.from(savedOrder);
    }
}
