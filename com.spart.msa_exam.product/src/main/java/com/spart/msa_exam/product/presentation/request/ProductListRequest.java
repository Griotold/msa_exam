package com.spart.msa_exam.product.presentation.request;

import com.spart.msa_exam.product.application.usecase.command.ProductListCommand;
import org.springframework.data.domain.Pageable;

public record ProductListRequest(String name, Pageable pageable) {

    public ProductListCommand toCommand() {
        return new ProductListCommand(name, pageable);
    }
}
