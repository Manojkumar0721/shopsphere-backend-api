# ShopSphere — Project Requirements & Architecture Plan

## 1. Project Overview & Scope
**ShopSphere** is an enterprise-style e-commerce RESTful backend API designed to follow industry best practices, layered clean architecture, and strict security and testability standards.

### Target Objectives:
- Master Spring Boot 3.x and modern Java backend engineering.
- Implement production-grade practices: DTO pattern, global error handling, transaction boundaries, JWT authentication, and automated testing.
- Build confidence in explaining architectural decisions in MNC / enterprise technical interviews.

---

## 2. System Architecture & Request Lifecycle

ShopSphere follows a **Layered Modular Monolith Architecture**:

```
[ Client: Postman / Frontend / Mobile ]
                   │
                   ▼  HTTP Request (JSON / URL Params / Headers)
      ┌─────────────────────────┐
      │    Security Filters     │  Spring Security & JWT Authentication Filter
      └────────────┬────────────┘
                   ▼
      ┌─────────────────────────┐
      │    Controller Layer     │  @RestController: Routes, HTTP codes, @Valid DTOs
      └────────────┬────────────┘
                   ▼
      ┌─────────────────────────┐
      │      Service Layer      │  @Service: Business rules, transactions (@Transactional)
      └────────────┬────────────┘
                   ▼
      ┌─────────────────────────┐
      │    Repository Layer     │  Spring Data JPA Repositories (CRUD & Custom queries)
      └────────────┬────────────┘
                   ▼
      ┌─────────────────────────┐
      │     Database Layer      │  MySQL 8 (Relational tables, foreign keys, indexes)
      └─────────────────────────┘
```

### Key Responsibilities by Layer:
1. **Controller Layer (`controller`)**: Only manages HTTP requests, extracts parameters/bodies, triggers validation, and delegates directly to services. Never contains core business algorithms and never accesses repositories directly.
2. **DTO Layer (`dto`)**: Decouples external API contracts from internal database representations, preventing over-fetching, mass-assignment vulnerabilities, and serialization infinite loops.
3. **Service Layer (`service`)**: Contains business rules (e.g., inventory deduction, order price calculation, discount logic) and coordinates transactions.
4. **Repository Layer (`repository`)**: Handles database persistence and queries via Spring Data JPA interfaces.
5. **Entity Layer (`model` / `entity`)**: JPA entities mapping to relational MySQL tables.
6. **Exception Layer (`exception`)**: Centralized `@RestControllerAdvice` converting exceptions into standardized, client-friendly error structures.

---

## 3. Environment & Compatibility Matrix

| Component | Installed / Target | Status / Assessment |
|---|---|---|
| **OS** | Windows 11 (AMD64) | Active environment |
| **JDK** | Oracle JDK 23.0.2 | Installed. Spring Boot 3.4.x configured targeting Java 21 bytecode. |
| **Maven** | Apache Maven 3.9.15 | Installed and healthy |
| **Database** | MySQL 8.0 (Service: `MySQL80`) | Installed and active on local system |
| **VCS** | Git 2.45.0 | Initialized and linked to GitHub remote |
| **Docker** | Planned | Will be addressed in Milestone 14 |

---

## 4. Master Milestone Checklist

- [x] **Milestone 0: Environment and Requirements** (Completed)
- [x] **Milestone 1: Spring Boot Foundation** (Completed & Verified)
- [x] **Milestone 2: MySQL and JPA Fundamentals** (Completed & Verified)
- [x] **Milestone 3: Product CRUD APIs** (Completed & Verified)
- [ ] **Milestone 4: Validation and Error Handling**
- [ ] **Milestone 5: DTOs and Application Structure**
- [ ] **Milestone 6: Categories and Database Relationships**
- [ ] **Milestone 7: Search, Filtering, Sorting, and Pagination**
- [ ] **Milestone 8: User Registration and Authentication**
- [ ] **Milestone 9: Authorization and Roles**
- [ ] **Milestone 10: Shopping Cart**
- [ ] **Milestone 11: Orders and Inventory**
- [ ] **Milestone 12: Automated Testing**
- [ ] **Milestone 13: API Documentation and Hardening**
- [ ] **Milestone 14: Docker and Deployment**
- [ ] **Milestone 15: Final Portfolio and Interview Preparation**

---

## 5. Professional Git Workflow Rules
1. Every milestone will reside on clean, logical commits.
2. Sensitive credentials (passwords, JWT secrets, local DB passwords) will never be tracked; environment variables or untracked configuration will be used.
3. Every commit message follows the Conventional Commits standard (e.g., `feat: ...`, `docs: ...`, `chore: ...`).
