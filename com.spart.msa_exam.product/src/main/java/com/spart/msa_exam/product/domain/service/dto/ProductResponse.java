package com.spart.msa_exam.product.domain.service.dto;

import com.spart.msa_exam.product.domain.entity.Product;

import java.io.Serializable;

public record ProductResponse(Long id, String name, Integer supplyPrice)

        implements Serializable {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getSupplyPrice());
    }
}
