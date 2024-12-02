package com.spart.msa_exam.order.infra.client.feign.dto;

public record ProductResponse(
        Long id,
        String name,
        Integer supplyPrice
) {
}
