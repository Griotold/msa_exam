package com.spart.msa_exam.product.presentation.controller;

import com.spart.msa_exam.product.application.common.ApiResponse;
import com.spart.msa_exam.product.application.usecase.ProductCreateUseCase;
import com.spart.msa_exam.product.domain.service.dto.ProductCreateResponse;
import com.spart.msa_exam.product.presentation.request.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductCreateUseCase productCreateUseCase;

    @PostMapping
    public ApiResponse<ProductCreateResponse> create(@RequestBody ProductCreateRequest request,
                                                     @RequestHeader(value = "X-User-Id", required = true) String userId,
                                                     @RequestHeader(value = "X-Role", required = true) String role) {
        if (!"MANAGER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
        }
        ProductCreateResponse response = productCreateUseCase.execute(request.toCommand());
        return ApiResponse.success(HttpStatus.OK, "Product Create success", response);
    }
}
