# 프로젝트 구조
- `order-service`
```
src/main/java/com/spart/msa_exam/order/
├── application/
│   ├── common/
│   │   ├── exception/
│   │   │   ├── ExceptionResponse.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   ├── OrderErrorCode.java
│   │   │   └── OrderException.java
│   │   └── ApiResponse.java
│   └── usecase/
│       ├── command/
│       │   ├── OrderCreateCommand.java
│       │   ├── OrderReadCommand.java
│       │   └── OrderUpdateCommand.java
│       ├── OrderCreateUseCase.java
│       ├── OrderReadUseCase.java
│       ├── OrderUpdateUseCase.java
│       └── UseCase.java
│
├── domain/
│   ├── entity/
│   │   ├── Order.java
│   │   └── OrderProduct.java
│   ├── repository/
│   │   ├── OrderRepository.java
│   │   └── OrderProductRepository.java
│   └── service/
│       ├── dto/
│       │   ├── OrderCreateResponse.java
│       │   ├── OrderProductResponse.java
│       │   └── OrderUpdateResponse.java
│       └── OrderService.java
│
├── infra/
│   ├── client/feign/
│   │   ├── config/
│   │   │   └── FeignClientConfig.java
│   │   ├── dto/
│   │   │   └── ProductResponse.java
│   │   └── ProductFeignClient.java
│   ├── config/redis/
│   │   └── RedisConfig.java
│   └── persistence/
│       ├── OrderJpaRepository.java
│       ├── OrderProductJpaRepository.java
│       ├── OrderProductRepositoryImpl.java
│       └── OrderRepositoryImpl.java
│
└── presentation/
    ├── controller/
    │   └── OrderController.java
    └── request/
        ├── OrderCreateRequest.java
        └── OrderUpdateRequest.java
```

---

# CI 파이프라인 구축
- `run-test.yml` 파일을 생성하여
- `develop`, `feature/*` 에 `push` 이벤트 감지시,
- `devleop`에 `pull_request` 이벤트 감지시, `test`가 돌아가도록 했다.
  

```
# Actions 이름 github 페이지에서 볼 수 있다.
name: Run Test

# Event Trigger 특정 액션 (Push, Pull_Request)등이 명시한 Branch에서 일어나면 동작을 수행한다.
on: 
    push:
        # 배열로 여러 브랜치를 넣을 수 있다.
        branches: [ develop, feature/* ]
    # github pull request 생성시
    pull_request:
        branches: 
            - develop # -로 여러 브랜치를 명시하는 것도 가능

    # 실제 어떤 작업을 실행할지에 대한 명시
jobs:
  test:
    runs-on: ubuntu-latest
    strategy:
      matrix:
        service: [auth, eureka_server, gateway, product, order]  # 테스트할 서비스들
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          distribution: 'adopt'
          java-version: '17'
      - name: Test ${{ matrix.service }}
        run: |
          cd com.spart.msa_exam.${{ matrix.service }}
          chmod +x ./gradlew
          ./gradlew clean test

```

---
# Why?

## 1. 레거시 모놀리식 서비스를 MSA 로 전환한다고 할 때, 어떠한 이유가 있을 수 있을까요 ?

- 하나의 마이크로서비스가 동작하지 않아도 다른 마이크로서비스에는 지장이 없게 된다.
- 확장하기 쉬운 구조가 된다.
- 배포가 쉬워지고 자주 배포할 수 있다.


## 2. 고객 주문 시 예상치 못한 오류로 인해 상품 서비스에서 재고를 차감 하였으나, 주문 서비스에서 에러가 발생해 주문이 정상적으로 완료되지 않았을 경우 어떻게 할 수 있을까요 ?

- 보상 트랜잭션이 필요할 것이다.
- `SAGA` 패턴, 잘은 모르지만 위의 문제를 해결하는 방법이라고 알고 있다.


## 3. 마이크로서비스 간의 통신에 대한 보안을 어떻게 보장할 수 있을까요?

- `API Gateway`를 활용해서 중앙화된 인증 및 인가를 통해 보안을 유지한다.


## 4. MSA 환경에서 서로의 서비스를 의존하게 되는 의존성 순환이 발생했을때 어떻게 해결할 수 있을까요 ?

- 의존성 순환을 방지하려면, `Facade` 계층이나, `UseCase`를 `service` 앞단에 두고, `service`를 조합해서 사용하는 방식이어야 한다.
- 본 프로젝트는 `UseCase`를 사용해서 의존성 순환이 발생하지 않도록 구현했다.
- `OrderCreateUseCase.java`
```
package com.spart.msa_exam.order.application.usecase;

import com.spart.msa_exam.order.application.common.exception.OrderErrorCode;
import com.spart.msa_exam.order.application.common.exception.OrderException;
import com.spart.msa_exam.order.application.usecase.command.OrderCreateCommand;
import com.spart.msa_exam.order.domain.service.OrderService;
import com.spart.msa_exam.order.domain.service.dto.OrderCreateResponse;
import com.spart.msa_exam.order.infra.client.feign.ProductFeignClient;
import com.spart.msa_exam.order.infra.client.feign.dto.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@UseCase
public class OrderCreateUseCase {
    private final OrderService orderService;
    private final ProductFeignClient productFeignClient;

    @CircuitBreaker(name = "orderApiCircuit", fallbackMethod = "fallbackExecute")
    public OrderCreateResponse execute(OrderCreateCommand command, Boolean isFail) {
        if (isFail != null && isFail) throw new OrderException(OrderErrorCode.ORDER_FAIL);
        log.info("OrderCreateUseCase.command : {}", command);
        List<ProductResponse> productResponses = productFeignClient.getProducts(command.productIds());
        log.info("OrderCreateUseCase.productResponses : {}", productResponses);
        return orderService.create(productResponses.stream().map(ProductResponse::id).toList());
    }

    public OrderCreateResponse fallbackExecute(OrderCreateCommand command, boolean isFail, Throwable t) {
        log.error("Fallback triggered for order command: {} due to: {}", command, t.getMessage());
        if (isFail) {
            throw new OrderException(OrderErrorCode.ORDER_FAIL);
        }
        throw new RuntimeException(t.getMessage());
    }
}
```
- `UseCase`를 도입해서, `Service`끼리 의존성 순환이 발생하지 않도록 한다.


## 5. 분산 시스템에서의 데이터 일관성을 어떻게 유지하나요?

- 데이터 일관성의 유형으로는 강한 일관성, 약한 일관성, 최종 일관성이 있다.
- 강한 일관성을 도입하면 데이터 일관성은 보장되만 성능이 낮아지고,
- 약한 일관성은 데이터 일관성은 보장할 수 없지만, 성능이 높아진다.
- 시스템의 특성에 따라 적절한 일관성 모델을 선택해야 한다.

---
# 프로젝트 하면서 생긴 질문들 - 피드백 부탁드려용!

## 0. 아키텍쳐 구조에 대해서 개선할 점이 있을까요?

## 1. `exception`을 어디다 두는 게 가장 좋을까요?
- 현재는 `application.common.exception`에 두고 있는데, `domain` 계층에서 `exception`을 의존하다보니 `application`을 의존하는 상황입니다.
- 아예 `exception`을 `domain`에 넣는 게 어떨지 생각해보았는데(왜냐하면, `exception`도 큰 틀에서 보면 `domain`이지 않을까... 하는 생각), 괜찮은 생각일까요?

## 2. `ApiResponse`는 어디다 두는 게 가장 좋을까요?
- 현재는 `application.common`에 있는데요.
- 고민고민하다가 그냥 여기다 넣었습니다.

## 3. `Circuit Breaker`랑 `Redis Cache`를 `UseCase`에서 적용하는게 맞는건지
- 강의에서는 `Service`에서 `CirCuit Breaker`랑 `Redis Cache`를 적용했는데요.
- 제 프로젝트는 `UseCase`가 `Service` 앞단에 있고,
- 도메인 계층인 `Service`는 순수한 비즈니스 로직만 남겨두는 게 맞을 거 같아서 `UseCase`에 적용했습니다.
- 이러한 접근이 옳은 접근인가요?
  


# References
https://velog.io/@sleekydevzero86/distributed-system-data-consistency

