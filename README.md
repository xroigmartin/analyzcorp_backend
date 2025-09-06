# AnalyzCorp - Backend

Backend of the **AnalyzCorp** project, a personal and family finance management platform.  
Developed with **Java 21**, **Spring Boot 3**, and **PostgreSQL 17** following **Hexagonal Architecture** and **DDD** principles.

---

## 🚀 Features
- REST API for financial account management.
- Hexagonal architecture with use cases, domain, and infrastructure layers.
- Flyway for database migrations.
- Centralized API responses (`ApiResponse` / `ApiError`).
- Unit tests with JUnit 5 and Mockito.

---

## 🛠️ Tech Stack
- **Language:** Java 21  
- **Framework:** Spring Boot 3.x  
- **Database:** PostgreSQL 17  
- **Migrations:** Flyway  
- **Tests:** JUnit 5, Mockito  
- **Build Tool:** Maven  

---

## 📂 Project Structure
```
backend/
 ├── src/
 │   ├── main/
 │   │   ├── java/...        # Application, Domain, Infrastructure
 │   │   └── resources/...   # Application.yml, Flyway migrations
 │   └── test/...            # Unit tests
 └── pom.xml
```

---

## ▶️ How to Run
1. Clone the repository
   ```bash
   git clone https://github.com/your-username/analyzcorp-backend.git
   ```
2. Configure PostgreSQL and set environment variables if needed.
3. Run migrations with Flyway.
4. Start the application:
   ```bash
   mvn spring-boot:run
   ```

---

## 🧪 Running Tests
```bash
mvn test
```

---

## 📜 Changelog
All notable changes to this project are documented in [CHANGELOG.md](./CHANGELOG.md).

---

## 📖 License
This project is currently distributed **without a license**.  
All rights are reserved by the author.
