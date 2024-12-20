package com.spart.msa_exam.order.application.usecase;

import com.spart.msa_exam.order.application.usecase.command.OrderUpdateCommand;
import com.spart.msa_exam.order.domain.service.OrderService;
import com.spart.msa_exam.order.domain.service.dto.OrderUpdateResponse;
import com.spart.msa_exam.order.infra.client.feign.ProductFeignClient;
import com.spart.msa_exam.order.infra.client.feign.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class OrderUpdateUseCase {

    private final OrderService orderService;
    private final ProductFeignClient productFeignClient;

    public OrderUpdateResponse execute(OrderUpdateCommand command) {
        log.info("OrderCreateUseCase.command : {}", command);
        List<ProductResponse> productResponses = productFeignClient.getProducts(command.productIds());
        log.info("OrderCreateUseCase.productResponses : {}", productResponses);
        return orderService.update(command.id(), productResponses.stream().map(ProductResponse::id).toList());
    }
}
