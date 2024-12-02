package com.spart.msa_exam.product.application.usecase;

import com.spart.msa_exam.product.application.usecase.command.ProductBatchGetCommand;
import com.spart.msa_exam.product.domain.service.ProductService;
import com.spart.msa_exam.product.domain.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class ProductBatchGetUseCase {

    private final ProductService productService;

    public List<ProductResponse> execute(ProductBatchGetCommand command) {
        log.info("ProductBatchGetUseCase.ProductBatchGetCommand: " + command);
        return productService.findAllinIds(command.productIds());
    }
}
