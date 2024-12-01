package com.spart.msa_exam.auth.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    MANAGER("관리자"), CUSTOMER("고객");

    private final String description;

    public static Role of(String request) {
        return switch (request) {
            case "manager" -> MANAGER;
            case "customer" -> CUSTOMER;
            default -> throw new IllegalStateException("Unexpected value: " + request);
        };
    }
}
