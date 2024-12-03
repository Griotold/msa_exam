package com.spart.msa_exam.order.domain.service.dto;

import com.spart.msa_exam.order.domain.entity.Order;

import java.util.List;

public record OrderCreateResponse(
        Long orderId,
        List<OrderProductResponse> orderProductResponses
) {
    public static OrderCreateResponse from(Order order) {
        return new OrderCreateResponse(order.getId(),
                order.getOrderProducts().stream().map(OrderProductResponse::from).toList());
    }
}
