package com.spart.msa_exam.order.infra.persistence;

import com.spart.msa_exam.order.domain.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {
}
