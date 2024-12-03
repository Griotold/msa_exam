package com.spart.msa_exam.product.application.usecase;

import com.spart.msa_exam.product.application.usecase.command.ProductCreateCommand;
import com.spart.msa_exam.product.domain.service.ProductService;
import com.spart.msa_exam.product.domain.service.dto.ProductCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class ProductCreateUseCase {

    private final ProductService productService;

    @CacheEvict(cacheNames = "productListCache", allEntries = true)
    public ProductCreateResponse execute(ProductCreateCommand command) {
        log.info("ProductCreateUseCase.ProductCreateCommand: " + command);
        return productService.create(command.name(), command.supplyPrice());
    }
}
