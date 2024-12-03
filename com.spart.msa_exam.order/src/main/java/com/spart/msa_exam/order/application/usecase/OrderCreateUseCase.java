package com.spart.msa_exam.order.application.usecase;

import com.spart.msa_exam.order.application.common.exception.OrderErrorCode;
import com.spart.msa_exam.order.application.common.exception.OrderException;
import com.spart.msa_exam.order.application.usecase.command.OrderCreateCommand;
import com.spart.msa_exam.order.domain.service.OrderService;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.infra.client.feign.ProductFeignClient;
import com.spart.msa_exam.order.infra.client.feign.dto.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class OrderCreateUseCase {
    private final OrderService orderService;
    private final ProductFeignClient productFeignClient;

    @CircuitBreaker(name = "orderApiCircuit", fallbackMethod = "fallbackExecute")
    public OrderCreateResponse execute(OrderCreateCommand command, Boolean isFail) {
        if (isFail != null && isFail) throw new OrderException(OrderErrorCode.ORDER_FAIL);
        log.info("OrderCreateUseCase.command : {}", command);
        List<ProductResponse> productResponses = productFeignClient.getProducts(command.productIds());
        log.info("OrderCreateUseCase.productResponses : {}", productResponses);
        return orderService.create(productResponses.stream().map(ProductResponse::id).toList());
    }

    public OrderCreateResponse fallbackExecute(OrderCreateCommand command, boolean isFail, Throwable t) {
        log.error("Fallback triggered for order command: {} due to: {}", command, t.getMessage());
        if (isFail) {
            throw new OrderException(OrderErrorCode.ORDER_FAIL);
        }
        throw new RuntimeException(t.getMessage());
    }
}
