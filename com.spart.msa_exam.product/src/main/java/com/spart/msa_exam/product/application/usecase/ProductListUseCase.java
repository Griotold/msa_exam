package com.spart.msa_exam.product.application.usecase;

import com.spart.msa_exam.product.application.usecase.command.ProductListCommand;
import com.spart.msa_exam.product.domain.service.ProductService;
import com.spart.msa_exam.product.domain.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class ProductListUseCase {

    private final ProductService productService;

    @Cacheable(
            cacheNames = "productListCache",
            key = "{ #command.name, #command.pageable.pageNumber, #command.pageable.pageSize }"
    )
    public Page<ProductResponse> execute(ProductListCommand command) {
        return productService.readAll(command.name(), command.pageable());
    }
}
