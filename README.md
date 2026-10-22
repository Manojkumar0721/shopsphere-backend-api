# ShopSphere — E-Commerce Backend RESTful API

ShopSphere is an enterprise-grade e-commerce RESTful API developed with Java 21 and Spring Boot 3. The project is designed with a layered clean architecture, incorporating JWT authentication, role-based authorization, inventory concurrency control, dynamic catalog filtering, centralized error handling, and comprehensive automated testing.

---

## Technical Stack

- **Language:** Java 21 LTS
- **Framework:** Spring Boot 3.4.3 (Spring MVC, Spring Data JPA, Spring Security)
- **Database:** MySQL 8.0
- **Build Tool:** Apache Maven
- **Testing:** JUnit 5, Mockito, MockMvc
- **API Documentation:** OpenAPI / Swagger UI
- **Containerization:** Docker & Docker Compose

---

## Architecture Overview

ShopSphere adheres strictly to a **Layered Modular Monolith Architecture**:

```
[ Client: Postman / Web / Mobile ]
               │
               ▼
   [ Security & JWT Filters ]
               │
               ▼
      [ Controller Layer ]      <-- HTTP endpoints, status codes, DTO validation
               │
               ▼
       [ Service Layer ]        <-- Business algorithms, transaction boundaries (@Transactional)
               │
               ▼
     [ Repository Layer ]       <-- Spring Data JPA database operations
               │
               ▼
      [ MySQL 8 Database ]
```

---

## Getting Started

### Prerequisites
- **JDK 21** or later
- **Apache Maven 3.9+**
- **MySQL 8.0**
- **Git**

### Build and Run Tests
Clone the repository and run:
```bash
git clone https://github.com/Manojkumar0721/shopsphere-backend-api.git
cd shopsphere-backend-api
mvn clean test
```

### Run Locally
```bash
mvn spring-boot:run
```
Once started, the application will listen on port `8080`.

### Health Check Endpoint
To verify the application status:
- **Method:** `GET`
- **URL:** `http://localhost:8080/api/v1/health`
- **Response:**
```json
{
  "status": "UP",
  "application": "ShopSphere API",
  "timestamp": "2026-10-19T03:43:49.285Z"
}
```

---

## Milestone Progress

- [x] **Milestone 0:** Environment and Requirements
- [x] **Milestone 1:** Spring Boot Foundation
- [x] **Milestone 2:** MySQL and JPA Fundamentals
- [x] **Milestone 3:** Product CRUD APIs
- [x] **Milestone 4:** Validation and Centralized Error Handling
- [ ] **Milestone 5:** DTOs and Application Decoupling
- [ ] **Milestone 5:** DTOs and Application Decoupling
- [ ] **Milestone 6:** Categories & Relational Mapping
- [ ] **Milestone 7:** Search, Filtering, Sorting & Pagination
- [ ] **Milestone 8:** User Registration & JWT Authentication
- [ ] **Milestone 9:** Authorization & Role-Based Access Control
- [ ] **Milestone 10:** Shopping Cart Management
- [ ] **Milestone 11:** Orders, Inventory & Transactions
- [ ] **Milestone 12:** Automated Testing Suite
- [ ] **Milestone 13:** API Documentation (OpenAPI/Swagger) & Hardening
- [ ] **Milestone 14:** Docker Containerization & Deployment
- [ ] **Milestone 15:** Portfolio Polish & Interview Readiness
