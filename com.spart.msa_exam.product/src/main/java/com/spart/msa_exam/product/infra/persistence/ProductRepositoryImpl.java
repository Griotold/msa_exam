package com.spart.msa_exam.product.infra.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spart.msa_exam.product.domain.entity.Product;
import com.spart.msa_exam.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.spart.msa_exam.product.domain.entity.QProduct.product;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Product> findById(Long id) {
        return productJpaRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return productJpaRepository.findByName(name);
    }

    @Override
    public Page<Product> findAll(String name, Pageable pageable) {

        List<Product> contents = queryFactory.selectFrom(product)
                .where(nameContains(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(product.count())
                .from(product)
                .where(nameContains(name)).fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    @Override
    public List<Product> findAllById(List<Long> ids) {
        return productJpaRepository.findAllById(ids);
    }

    private BooleanExpression nameContains(String name) {
        return name != null ? product.name.containsIgnoreCase(name) : null;
    }
}
