package com.spart.msa_exam.product.domain.service.dto;

import com.spart.msa_exam.product.domain.entity.Product;

public record ProductResponse(String name, Integer supplyPrice) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getName(), product.getSupplyPrice());
    }
}
