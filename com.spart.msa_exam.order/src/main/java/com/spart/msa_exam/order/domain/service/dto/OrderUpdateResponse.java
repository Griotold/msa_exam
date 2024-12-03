package com.spart.msa_exam.order.domain.service.dto;

import com.spart.msa_exam.order.domain.entity.Order;

import java.util.List;

public record OrderUpdateResponse(
        Long orderId,
        List<OrderProductResponse> orderProductResponses
) {
    public static OrderUpdateResponse from(Order order) {
        return new OrderUpdateResponse(order.getId(),
                order.getOrderProducts().stream().map(OrderProductResponse::from).toList());
    }
}
