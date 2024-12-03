package com.spart.msa_exam.order.application.usecase.command;

import java.util.List;

public record OrderCreateCommand(List<Long> productIds) {
}
