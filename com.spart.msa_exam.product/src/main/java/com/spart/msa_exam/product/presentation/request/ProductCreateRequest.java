package com.spart.msa_exam.product.presentation.request;

import com.spart.msa_exam.product.application.usecase.command.ProductCreateCommand;

public record ProductCreateRequest(
        String name,
        Integer supplyPrice
) {

    public ProductCreateCommand toCommand() {
        return new ProductCreateCommand(name, supplyPrice);
    }
}
