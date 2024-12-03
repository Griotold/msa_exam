package com.spart.msa_exam.order.application.usecase;

import com.spart.msa_exam.order.application.usecase.command.OrderReadCommand;
import com.spart.msa_exam.order.domain.service.OrderService;
import com.spart.msa_exam.order.domain.service.dto.OrderProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class OrderReadUseCase {

    private final OrderService orderService;

    public List<OrderProductResponse> execute(OrderReadCommand command) {
        log.info("OrderReadUseCase.command : {}", command);
        return orderService.getOrderProducts(command.id());
    }
}
