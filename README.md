# 💳 CaixaBank Banking API — Extended Edition

---

### 📘 Overview

This **Extended Edition** of the original CaixaBank Banking API was created as a **personal improvement project**, expanding on the original hackathon challenge to make it cleaner, more structured, and easier to maintain.

The project improves organization, documentation, and testing while preserving the original logic.  
It was built to **consolidate backend skills** and apply practices closer to professional backend development.

---

### 🧭 Purpose of the Extended Version

This version was developed to:
- Reinforce backend fundamentals with **Spring Boot 3** and **Java 21**.
- Apply clean architecture principles with DTOs, mappers, and layered services.
- Add **Swagger/OpenAPI** for interactive API documentation.
- Include **unit and repository tests** to validate business logic.
- Configure **Docker** and multi-environment profiles for real-world deployment scenarios.

In short, this edition turns the initial hackathon code into a **well-structured, documented, and tested backend** ready for a portfolio or production baseline.

---

### ⚙️ Tech Stack

| Layer | Technologies                                                                                       |
|-------|----------------------------------------------------------------------------------------------------|
| **Core Framework** | Spring Boot 3, Java 21                                                                             |
| **Build Tool** | Maven                                                                                              |
| **Database** | MySQL (via Docker Compose)                                                                         |
| **ORM** | Spring Data JPA, Hibernate                                                                         |
| **Auth & Security** | Spring Security, JWT                                                                               |
| **Documentation** | Springdoc OpenAPI (Swagger UI)                                                                     |
| **Testing** | JUnit 5, Mockito, Spring Boot Test                                                                 |
| **Containerization** | Dockerfile + Docker Compose                                                                        |
| **Configuration** | `.env`, `application-dev.properties`, `application-prod.properties`, `application-test.properties` |

---

### 🧩 Key Improvements

| Area | Enhancement |
|------|--------------|
| **Documentation** | Added **Swagger UI** with JWT authentication support |
| **Environment Setup** | Added `.env` and separate profiles (`dev`, `prod`, `test`) |
| **Testing** | Created **unit and repository tests** with JUnit and Mockito |
| **Error Handling** | Centralized exceptions using `GlobalExceptionHandler` |
| **Deployment** | Configured Docker for both MySQL and the Spring app |
| **Code Quality** | Improved readability, modularity, and package structure |

---

### 🧠 Learning Outcomes

- Strengthened understanding of **Spring Boot modular architecture**.
- Practiced **clean code principles** and multi-layered design.
- Gained experience with **Swagger**, **testing**, and **Docker deployment**.
- Enhanced ability to maintain and scale backend applications.

---

### 🚀 How to Run

**Option 1 — Run with Docker**
```bash
docker-compose up --build
```

Access at: http://localhost:3000/swagger-ui

**Option 2 — Run in Dev Mode**
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

---

### 📑 Swagger API Documentation

- Strengthened understanding of **Spring Boot modular architecture**.
- Practiced **clean code principles** and multi-layered design.
- Gained experience with **Swagger**, **testing**, and **Docker deployment**.
- Enhanced ability to maintain and scale backend applications.

**Accessible at:**

➡ http://localhost:3000/swagger-ui

**Includes endpoints for:**
- 	🔐 Authentication (login/logout)
- 	👤 User registration
-	💳 Account management (create Main or Invest account)
-	💰 Transactions (deposit, withdraw, transfer)

---

### 👨‍💻 Author

Kenzo de Albuquerque 
<br />
Software Engineer | Java & Spring Boot Developer 
<br />
📧 kenzoalbuqk@gmail.com
<br />
🔗 github.com/OzneKx

---

### 🏁 Final Note

This project represents the consolidation of my backend foundation — bridging academic projects and production standards.
The next step will focus on evolving these concepts into a microservices architecture, applying Spring Cloud, messaging, and distributed tracing.
