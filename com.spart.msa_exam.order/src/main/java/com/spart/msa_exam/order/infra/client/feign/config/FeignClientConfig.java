package com.spart.msa_exam.order.infra.client.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.spart.msa_exam.order.infra.client.feign")
public class FeignClientConfig {
}
