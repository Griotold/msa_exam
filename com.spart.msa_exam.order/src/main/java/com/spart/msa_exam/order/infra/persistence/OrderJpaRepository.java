package com.spart.msa_exam.order.infra.persistence;

import com.spart.msa_exam.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {
}
