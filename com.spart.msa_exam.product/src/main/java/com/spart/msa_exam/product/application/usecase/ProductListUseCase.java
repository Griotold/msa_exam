package com.spart.msa_exam.product.application.usecase;

import com.spart.msa_exam.product.application.usecase.command.ProductListCommand;
import com.spart.msa_exam.product.domain.service.ProductService;
import com.spart.msa_exam.product.domain.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class ProductListUseCase {

    private final ProductService productService;

    public Page<ProductResponse> execute(ProductListCommand command) {
        return productService.readAll(command.name(), command.pageable());
    }
}
