# 💰 FinTrack API

> Personal finance management REST API built with Java 17 and Spring Boot.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org)
[![JWT](https://img.shields.io/badge/Auth-JWT-yellow)](https://jwt.io)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)](https://www.docker.com)

---

## 📋 About

FinTrack API is a backend solution for personal finance management. It allows users to manage bank accounts, record income and expenses, and track their financial activity — built with a clean layered architecture and industry-standard security practices.

This project combines backend development expertise with real-world financial domain knowledge, making it especially suitable for FinTech and ERP solutions.

---

## ✨ Features

- 🔐 User registration and login with JWT authentication
- 🏦 Multiple account types: Savings, Checking, Cash, Credit Card
- 💸 Transaction recording with automatic balance updates
- 📊 Transaction filtering by account and type
- 🛡️ Protected endpoints with Spring Security
- 🐳 PostgreSQL database with Docker Compose
- ✅ Input validation with Jakarta Validation

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17 |
| Framework | Spring Boot 3.5 |
| Security | Spring Security + JWT (jjwt 0.11.5) |
| Persistence | Spring Data JPA + Hibernate |
| Database | PostgreSQL 15 |
| Containerization | Docker + Docker Compose |
| Build Tool | Maven |
| Utilities | Lombok |

---

## 🏗️ Architecture

```
src/main/java/com/fintrack/fintrack_api/
├── controller/         # REST Controllers (API layer)
├── service/            # Business logic
├── repository/         # Data access layer (Spring Data JPA)
├── model/              # JPA Entities
├── dto/                # Data Transfer Objects
├── security/           # JWT filter, Security config
└── exception/          # Exception handling
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker Desktop

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/camiladev-cd/fintrack-api.git
cd fintrack-api
```

**2. Start the database**
```bash
docker compose up -d
```

**3. Run the application**
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login and get JWT token | No |

### Accounts
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/accounts` | Create new account | Yes |
| GET | `/api/accounts` | Get all user accounts | Yes |
| GET | `/api/accounts/{id}` | Get account by ID | Yes |
| DELETE | `/api/accounts/{id}` | Delete account | Yes |

### Transactions
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/transactions` | Create transaction | Yes |
| GET | `/api/transactions/account/{id}` | Get transactions by account | Yes |
| GET | `/api/transactions/account/{id}/type/{type}` | Filter by type | Yes |

---

## 📝 Usage Examples

### Register
```bash
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "john@example.com",
  "name": "John Doe"
}
```

### Create Account
```bash
POST /api/accounts
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Main Savings",
  "type": "SAVINGS",
  "initialBalance": 1000.00
}
```

### Create Transaction
```bash
POST /api/transactions
Authorization: Bearer {token}
Content-Type: application/json

{
  "amount": 500.00,
  "description": "Monthly salary",
  "type": "INCOME",
  "category": "SALARY",
  "accountId": 1
}
```

---

## 🗂️ Transaction Types & Categories

**Types:** `INCOME` · `EXPENSE` · `TRANSFER`

**Categories:** `FOOD` · `TRANSPORT` · `HOUSING` · `HEALTH` · `ENTERTAINMENT` · `SALARY` · `SAVINGS` · `TRANSFER` · `OTHER`

**Account Types:** `SAVINGS` · `CHECKING` · `CASH` · `CREDIT_CARD`

---

## ⚙️ Configuration

The application uses the following environment variables (configured in `application.yml`):

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/fintrack
    username: fintrack_user
    password: fintrack_pass

application:
  security:
    jwt:
      secret-key: your-secret-key
      expiration: 86400000  # 24 hours
```

---

## 👩‍💻 Author

**Camila Dueñas** — Backend Developer specialized in Java & Spring Boot  
🔗 [LinkedIn](https://linkedin.com/in/camiladuenas-dev) · [GitHub](https://github.com/camiladev-cd)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).
