package com.spart.msa_exam.product.domain.service;

import com.spart.msa_exam.product.application.common.exception.ProductErrorCode;
import com.spart.msa_exam.product.application.common.exception.ProductException;
import com.spart.msa_exam.product.domain.entity.Product;
import com.spart.msa_exam.product.domain.repository.ProductRepository;
import com.spart.msa_exam.product.domain.service.dto.ProductCreateResponse;
import com.spart.msa_exam.product.domain.service.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductCreateResponse create(String name, Integer supplyPrice) {
        log.info("try create product name: {}, supplyPrice: {}", name, supplyPrice);
        if (productRepository.findByName(name).isPresent()) {
            throw new ProductException(ProductErrorCode.DUPLICATE_PRODUCT_NAME);
        }
        return ProductCreateResponse.from(productRepository.save(Product.create(name, supplyPrice)));
    }

    public Page<ProductResponse> readAll(String name, Pageable pageable) {
        return productRepository.findAll(name, pageable).map(ProductResponse::from);
    }

    public List<ProductResponse> findAllinIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);

        // 요청한 상품 ID 개수와 찾은 상품 개수가 다르면 예외 발생
        if (products.size() != ids.size()) {
            throw new ProductException(ProductErrorCode.PRODUCT_NOT_FOUND);
        }

        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}
