package com.spart.msa_exam.order.application.usecase;

import com.spart.msa_exam.order.application.usecase.command.OrderReadCommand;
import com.spart.msa_exam.order.domain.service.OrderService;
import com.spart.msa_exam.order.domain.service.dto.OrderProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class OrderReadUseCase {

    private final OrderService orderService;

    @Cacheable(cacheNames = "orderCache", key = "#command.id")
    public List<OrderProductResponse> execute(OrderReadCommand command) {
        log.info("OrderReadUseCase.command : {}", command);
        return orderService.getOrderProducts(command.id());
    }
}
