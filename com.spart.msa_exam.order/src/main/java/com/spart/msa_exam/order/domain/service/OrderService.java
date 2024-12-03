package com.spart.msa_exam.order.domain.service;

import com.spart.msa_exam.order.application.common.exception.OrderErrorCode;
import com.spart.msa_exam.order.application.common.exception.OrderException;
import com.spart.msa_exam.order.domain.entity.Order;
import com.spart.msa_exam.order.domain.entity.OrderProduct;
import com.spart.msa_exam.order.domain.repository.OrderRepository;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.domain.service.dto.OrderUpdateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public OrderCreateResponse create(List<Long> productIds) {
        log.info("Creating order with product IDs: {}", productIds);
        if (productIds == null || productIds.isEmpty()) {
            throw new OrderException(OrderErrorCode.INVALID_PRODUCT_LIST);
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

    @Transactional
    public OrderUpdateResponse update(Long id, List<Long> productIds) {
        log.info("Updating order with product IDs: {}", productIds);
        if (productIds == null || productIds.isEmpty()) {
            throw new OrderException(OrderErrorCode.INVALID_PRODUCT_LIST);
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));

        List<OrderProduct> newOrderProducts = productIds.stream()
                .map(productId -> OrderProduct.create(order, productId))
                .collect(Collectors.toList());

        order.updateOrderProducts(newOrderProducts);

        Order updatedOrder = orderRepository.save(order);
        log.info("Order updated successfully with ID: {}", updatedOrder.getId());

        return OrderUpdateResponse.from(updatedOrder);


    }
}
