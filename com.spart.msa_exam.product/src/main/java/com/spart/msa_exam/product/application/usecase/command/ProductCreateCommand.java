package com.spart.msa_exam.product.application.usecase.command;

public record ProductCreateCommand(
        String name,
        Integer supplyPrice
) {
}
