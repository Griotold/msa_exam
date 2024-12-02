package com.spart.msa_exam.order.presentation.controller;

import com.spart.msa_exam.order.application.common.ApiResponse;
import com.spart.msa_exam.order.application.usecase.OrderCreateUseCase;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.presentation.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderCreateUseCase orderCreateUseCase;

    @PostMapping
    public ApiResponse<?> create(@RequestBody OrderCreateRequest request) {
        OrderCreateResponse response = orderCreateUseCase.execute(request.toCommand());
        return ApiResponse.success(HttpStatus.OK, "Create Order Success", response);
    }
}
