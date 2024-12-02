package com.spart.msa_exam.product.domain.service;

import com.spart.msa_exam.product.application.common.exception.ProductException;
import com.spart.msa_exam.product.domain.entity.Product;
import com.spart.msa_exam.product.domain.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    @Test
    @DisplayName("상품 생성 성공")
    void createProductSuccess() {
        // given
        String productName = "새로운 상품";
        Integer price = 15000;

        // when
        productService.create(productName, price);

        // then
        Optional<Product> foundProduct = productRepository.findByName(productName);
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo(productName);
        assertThat(foundProduct.get().getSupplyPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("상품 생성 실패 - 중복된 상품명")
    void createProductFailDuplicateName() {
        // given
        String existingName = "existingName";
        Integer price = 15000;
        Integer anotherPrice = 20000;
        productService.create(existingName, price);

        // when & then
        assertThatThrownBy(() -> productService.create(existingName, anotherPrice))
                .isInstanceOf(ProductException.class)
                .hasFieldOrPropertyWithValue("message", "상품 이름이 이미 존재합니다.");
    }
}