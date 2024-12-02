package com.spart.msa_exam.product.domain.service.dto;

import com.spart.msa_exam.product.domain.entity.Product;

public record ProductCreateResponse(
        String name,
        Integer supplyPrice
) {

    public static ProductCreateResponse from(Product product) {
        return new ProductCreateResponse(product.getName(), product.getSupplyPrice());
    }
}
