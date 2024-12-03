package com.spart.msa_exam.order.presentation.request;

import com.spart.msa_exam.order.application.usecase.command.OrderUpdateCommand;

import java.util.List;

public record OrderUpdateRequest(List<Long> productIds) {

    public OrderUpdateCommand toCommand(Long id) {
        return new OrderUpdateCommand(id, productIds);
    }
}
