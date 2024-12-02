package com.spart.msa_exam.product.presentation.controller;

import com.spart.msa_exam.product.application.common.ApiResponse;
import com.spart.msa_exam.product.application.common.exception.ProductErrorCode;
import com.spart.msa_exam.product.application.common.exception.ProductException;
import com.spart.msa_exam.product.application.usecase.ProductCreateUseCase;
import com.spart.msa_exam.product.application.usecase.ProductListUseCase;
import com.spart.msa_exam.product.application.usecase.command.ProductListCommand;
import com.spart.msa_exam.product.domain.service.dto.ProductCreateResponse;
import com.spart.msa_exam.product.domain.service.dto.ProductResponse;
import com.spart.msa_exam.product.presentation.request.ProductCreateRequest;
import com.spart.msa_exam.product.presentation.request.ProductListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductCreateUseCase productCreateUseCase;
    private final ProductListUseCase productListUseCase;

    @PostMapping
    public ApiResponse<ProductCreateResponse> create(@RequestBody ProductCreateRequest request,
                                                     @RequestHeader(value = "X-User-Id", required = true) String userId,
                                                     @RequestHeader(value = "X-Role", required = true) String role) {
        if (!"MANAGER".equals(role)) {
            throw new ProductException(ProductErrorCode.FORBIDDEN_ACCESS);
        }
        ProductCreateResponse response = productCreateUseCase.execute(request.toCommand());
        return ApiResponse.success(HttpStatus.OK, "Product Create success", response);
    }

    @GetMapping
    public ApiResponse<Page<ProductResponse>> search(@RequestParam(required = false) String name, Pageable pageable) {
        Page<ProductResponse> response = productListUseCase.execute(new ProductListRequest(name, pageable).toCommand());
        return ApiResponse.success(HttpStatus.OK, "Get Product List", response);
    }
}
