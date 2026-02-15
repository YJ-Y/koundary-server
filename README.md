# Koundary Server

Spring Boot 기반의 인증(Auth) 기능을 포함한 백엔드 서버입니다.  
로컬 MySQL과 연동하여 회원 정보를 저장하고, JWT 기반 인증 흐름을 학습/구현했습니다.

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Web, Spring Data JPA
- MySQL
- Gradle

---

## Project Structure
```
src/main/java/com/koundary/koundaryserver
├─ auth
│ ├─ controller
│ └─ domain
└─ ...

---

## Getting Started (Local)

### 1) Requirements
- Java 17
- MySQL (로컬 실행)

### 2) 설정 파일 준비 
이 레포에는 **민감 정보 유출 방지**를 위해  
`src/main/resources/application.yml` / `application.properties`를 커밋하지 않습니다.

대신 템플릿 파일인 아래를 제공합니다.

- `src/main/resources/application-example.yml`
