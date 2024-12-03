package com.spart.msa_exam.order.domain.service;

import com.spart.msa_exam.order.domain.entity.Order;
import com.spart.msa_exam.order.domain.repository.OrderRepository;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @DisplayName("주문 성공")
    @Test
    void createOrder_ShouldSaveOrderWithProducts() {
        // Given
        List<Long> productIds = Arrays.asList(1L, 2L, 3L);

        // When
        OrderCreateResponse response = orderService.create(productIds);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.orderId()).isNotNull();

        Order savedOrder = orderRepository.findById(response.orderId()).orElseThrow();
        assertThat(savedOrder.getOrderProducts()).hasSize(productIds.size());
    }

}