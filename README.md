# 전체 어플리케이션 개요
<img width="752" height="372" alt="image" src="https://github.com/user-attachments/assets/80198e14-97c5-4bfa-a333-a3cde87ea29f" />

- `카테고리 서비스` ,`유저서비스` , `상품 서비스` 예시를 기반으로 springCloud msa 와 Kafka 를 통한 서비스 적용해보기.

# Spirng cloud Gateway
- `spring-cloud-starter-gateway-server-webflux`
- spring-cloid-starter-gateway-server-mvc
- `FilterConfig`를 설정하여 requestHeader , responseHeader 적용하기
  - yaml 설정을 통해 header 제어
- `CustomFilter`를 적용하여 Request ,Response 로그 적용하기
  - webflux 적용시 ServerltHttpRquest 가 아닌 `ServerHttpRequest` , `ServerHttpResponse` 사용
  <img width="1879" height="374" alt="image" src="https://github.com/user-attachments/assets/cee063c9-5911-4ff6-941c-7fa6e85383fa" />
- Filter 별 우선순위 `application.yaml` 기준 default-filter 지정fitler(GolbalFilter)-> routes : filters 의 순서 대로 진행.
<img width="886" height="591" alt="image" src="https://github.com/user-attachments/assets/470692d7-6db6-4cf9-89a9-d30c9acf417d" />



# MSA Eureka 기반 멀티 모듈 프로젝트

Spring Boot + Spring Cloud Netflix Eureka를 활용한  
**멀티 모듈 기반 Service Discovery 구성 및 동작 검증 프로젝트**입니다.

---

## 📁 프로젝트 구조

```text
root
 ├─ service-discovery      # Eureka Server
 ├─ user-service           # Discovery Client 테스트 서비스
 ├─ first-service          # Discovery Client
 └─ second-service         # Discovery Client
```

- Gradle 멀티 모듈 구조
- 각 모듈은 독립 실행 가능한 Spring Boot 애플리케이션

## 🧩 모듈 설명

1️⃣ service-discovery

- Eureka Server   : 모든 마이크로서비스의 등록 및 상태 관리 담당
- 주요 특징 @EnableEurekaServer 적용 , 자기 자신은 Discovery Client로 등록하지 않음
- Dashboard http://localhost:8761

2️⃣ user-service

- Eureka Discovery Client  : Service Discovery 동작 확인을 위한 테스트 서비스
- 역할 : Eureka Server에 정상 등록 여부 검증
 - 다른 서비스에서 조회 가능한 서비스

3️⃣ first-service
- Eureka Discovery Client : 간단한 API 호출을 통해 Discovery 동작 확인
- API : GET /welcome , GET /message  . GET /check

4️⃣ second-service
- Eureka Discovery Client
- first-service와 동일한 목적의 테스트 서비스-
- API : GET /welcome . GET /message , GET /check

🔄 서비스 실행 흐름

- service-discovery 실행 (Eureka Server)
- user-service, first-service, second-service 실행
- 각 서비스가 Eureka Server에 자동 등록
- Eureka Dashboard에서 서비스 목록 및 상태 확인
- 각 서비스의 API 호출을 통해 정상 동작 검증
