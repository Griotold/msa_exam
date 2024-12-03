package com.spart.msa_exam.order.infra.client.feign;

import com.spart.msa_exam.order.infra.client.feign.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @GetMapping("/products/batch")
    List<ProductResponse> getProducts(@RequestParam List<Long> productIds);
}
