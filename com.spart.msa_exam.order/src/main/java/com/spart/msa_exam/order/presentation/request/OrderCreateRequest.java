package com.spart.msa_exam.order.presentation.request;

import com.spart.msa_exam.order.application.usecase.command.OrderCreateCommand;

import java.util.List;

public record OrderCreateRequest(
        List<Long> productIds
) {
    public OrderCreateCommand toCommand() {
        return new OrderCreateCommand(productIds);
    }
}
