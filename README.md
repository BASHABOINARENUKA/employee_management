# Employee Management System

A backend Employee Management System built using **Spring Boot**, **Spring Data JPA**, **MySQL**, and **Spring Security with JWT authentication**.

The project is designed to demonstrate how a real-world Spring Boot REST API can be structured using layered architecture, database persistence, authentication, authorization, exception handling, and JPA relationships.

---

## 📌 Project Overview

The Employee Management System provides REST APIs for managing:

- Departments
- Employees
- Users
- Roles
- Authentication
- JWT-based authorization

The application follows a layered architecture where responsibilities are separated between:

- Controllers
- Services
- Repositories
- Entities
- Security
- Exception Handling
- Configuration

The main objective of this project is to understand how the different components of a Spring Boot backend work together to build a secure REST API.

---

# 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database interaction |
| Hibernate | ORM implementation |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| BCrypt | Password hashing |
| MySQL | Relational database |
| Maven | Dependency management and build |
| IntelliJ IDEA | Development environment |
| JUnit | Testing |

---

# 🏗️ Application Architecture

The application follows a layered architecture:

```text
                    Client
                      |
                      | HTTP Request
                      ↓
              ┌─────────────────┐
              │   Controller    │
              └─────────────────┘
                      |
                      ↓
              ┌─────────────────┐
              │    Service      │
              └─────────────────┘
                      |
                      ↓
              ┌─────────────────┐
              │   Repository    │
              └─────────────────┘
                      |
                      ↓
              ┌─────────────────┐
              │      JPA        │
              │   / Hibernate   │
              └─────────────────┘
                      |
                      ↓
              ┌─────────────────┐
              │     MySQL       │
              └─────────────────┘
