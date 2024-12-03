package com.spart.msa_exam.order.presentation.controller;

import com.spart.msa_exam.order.application.common.ApiResponse;
import com.spart.msa_exam.order.application.usecase.OrderCreateUseCase;
import com.spart.msa_exam.order.application.usecase.OrderReadUseCase;
import com.spart.msa_exam.order.application.usecase.OrderUpdateUseCase;
import com.spart.msa_exam.order.application.usecase.command.OrderReadCommand;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.domain.service.dto.OrderProductResponse;
import com.spart.msa_exam.order.domain.service.dto.OrderUpdateResponse;
import com.spart.msa_exam.order.presentation.request.OrderCreateRequest;
import com.spart.msa_exam.order.presentation.request.OrderUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderCreateUseCase orderCreateUseCase;
    private final OrderUpdateUseCase orderUpdateUseCase;
    private final OrderReadUseCase orderReadUseCase;

    @PostMapping
    public ApiResponse<OrderCreateResponse> create(@RequestBody OrderCreateRequest request,
                                                   @RequestParam(name = "fail", required = false) Boolean isfail) {
        OrderCreateResponse response = orderCreateUseCase.execute(request.toCommand(), isfail);
        return ApiResponse.success(HttpStatus.OK, "Create Order Success", response);
    }

    @PutMapping("/{orderId}")
    public ApiResponse<OrderUpdateResponse> update(@PathVariable("orderId") Long id,
                                                   @RequestBody OrderUpdateRequest request) {
        OrderUpdateResponse response = orderUpdateUseCase.execute(request.toCommand(id));
        return ApiResponse.success(HttpStatus.OK, "Update Order Success", response);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<List<OrderProductResponse>> getOrder(@PathVariable("orderId") Long id) {
        List<OrderProductResponse> response = orderReadUseCase.execute(new OrderReadCommand(id));
        return ApiResponse.success(HttpStatus.OK, "Get Order Success", response);
    }
}
