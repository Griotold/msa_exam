package com.spart.msa_exam.product.application.usecase.command;

import java.util.List;

public record ProductBatchGetCommand(
        List<Long> productIds
) {
}
