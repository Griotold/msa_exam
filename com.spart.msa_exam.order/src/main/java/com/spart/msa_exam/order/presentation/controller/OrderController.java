package com.spart.msa_exam.order.presentation.controller;

import com.spart.msa_exam.order.application.common.ApiResponse;
import com.spart.msa_exam.order.application.usecase.OrderCreateUseCase;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.presentation.request.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderCreateUseCase orderCreateUseCase;

    @PostMapping
    public ApiResponse<OrderCreateResponse> create(@RequestBody OrderCreateRequest request,
                                                   @RequestParam(name = "fail", required = false) Boolean isfail) {
        OrderCreateResponse response = orderCreateUseCase.execute(request.toCommand(), isfail);
        return ApiResponse.success(HttpStatus.OK, "Create Order Success", response);
    }
}
