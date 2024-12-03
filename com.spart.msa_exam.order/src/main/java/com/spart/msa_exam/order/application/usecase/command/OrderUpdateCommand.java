package com.spart.msa_exam.order.application.usecase.command;

import java.util.List;

public record OrderUpdateCommand(
        Long id,
        List<Long> productIds
) {
}
