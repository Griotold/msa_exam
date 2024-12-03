package com.spart.msa_exam.order.domain.service;

import com.spart.msa_exam.order.application.common.exception.OrderException;
import com.spart.msa_exam.order.domain.entity.Order;
import com.spart.msa_exam.order.domain.repository.OrderRepository;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.domain.service.dto.OrderProductResponse;
import com.spart.msa_exam.order.domain.service.dto.OrderUpdateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @DisplayName("주문 상품 업데이트 성공")
    @Test
    void updateOrder_ShouldAddNewProducts() {
        // Given
        List<Long> initialProductIds = Arrays.asList(1L, 2L);
        OrderCreateResponse createResponse = orderService.create(initialProductIds);

        List<Long> newProductIds = Arrays.asList(1L, 2L, 3L);

        // When
        OrderUpdateResponse updateResponse = orderService.update(createResponse.orderId(), newProductIds);

        // Then
        assertThat(updateResponse).isNotNull();
        assertThat(updateResponse.orderId()).isEqualTo(createResponse.orderId());

        Order updatedOrder = orderRepository.findById(updateResponse.orderId()).orElseThrow();
        assertThat(updatedOrder.getOrderProducts()).hasSize(newProductIds.size());
    }

    @DisplayName("존재하지 않는 주문 업데이트 시 예외 발생")
    @Test
    void updateOrder_ShouldThrowException_WhenOrderNotFound() {
        // Given
        Long nonExistentOrderId = 999L;
        List<Long> productIds = Arrays.asList(1L, 2L, 3L);

        // When & Then
        assertThatThrownBy(() -> orderService.update(nonExistentOrderId, productIds))
                .isInstanceOf(OrderException.class)
                .hasFieldOrPropertyWithValue("message", "주문을 찾을 수 없습니다.");
    }

    @DisplayName("빈 상품 리스트로 주문 업데이트 시 예외 발생")
    @Test
    void updateOrder_ShouldThrowException_WhenProductListEmpty() {
        // Given
        OrderCreateResponse createResponse = orderService.create(Arrays.asList(1L, 2L));
        List<Long> emptyProductIds = new ArrayList<>();

        // When & Then
        assertThatThrownBy(() -> orderService.update(createResponse.orderId(), emptyProductIds))
                .isInstanceOf(OrderException.class)
                .hasFieldOrPropertyWithValue("message", "상품 ID 리스트는 null이거나 비어 있을 수 없습니다.");
    }

    @DisplayName("주문 상품 목록 조회 성공")
    @Test
    void getOrderProducts_ShouldReturnOrderProducts() {
        // Given
        List<Long> productIds = Arrays.asList(1L, 2L, 3L);
        OrderCreateResponse createResponse = orderService.create(productIds);

        // When
        List<OrderProductResponse> orderProducts = orderService.getOrderProducts(createResponse.orderId());

        // Then
        assertThat(orderProducts).isNotNull();
        assertThat(orderProducts).hasSize(productIds.size());
        assertThat(orderProducts.stream().map(OrderProductResponse::productId).collect(Collectors.toList()))
                .containsExactlyInAnyOrderElementsOf(productIds);
    }

    @DisplayName("존재하지 않는 주문의 상품 목록 조회 시 예외 발생")
    @Test
    void getOrderProducts_ShouldThrowException_WhenOrderNotFound() {
        // Given
        Long nonExistentOrderId = 999L;

        // When & Then
        assertThatThrownBy(() -> orderService.getOrderProducts(nonExistentOrderId))
                .isInstanceOf(OrderException.class)
                .hasFieldOrPropertyWithValue("message", "주문을 찾을 수 없습니다.");
    }
}