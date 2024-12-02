package com.spart.msa_exam.product.application.usecase.command;

import org.springframework.data.domain.Pageable;

public record ProductListCommand(String name, Pageable pageable) {
}
