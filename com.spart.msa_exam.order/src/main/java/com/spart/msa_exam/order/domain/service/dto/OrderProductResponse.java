package com.spart.msa_exam.order.domain.service.dto;

import com.spart.msa_exam.order.domain.entity.OrderProduct;

public record OrderProductResponse(
        Long orderProductId,
        Long orderId,
        Long productId
) {

    public static OrderProductResponse from(OrderProduct orderProduct) {
        return new OrderProductResponse(
                orderProduct.getId(),
                orderProduct.getOrder().getId(),
                orderProduct.getProductId());
    }
}
