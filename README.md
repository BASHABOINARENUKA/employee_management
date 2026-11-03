# Employee Management System

A full-stack Employee Management System built using **React.js, Spring Boot, Spring Data JPA, MySQL, and Spring Security with JWT Authentication**.

The application provides a role-based interface for managing employees and departments. It follows a layered backend architecture and a component-based frontend architecture, demonstrating frontend-backend integration, REST API development, database persistence, authentication, authorization, and CRUD operations.

---

## Project Overview

The Employee Management System provides functionality for managing:

- Employees
- Departments
- Users
- Roles
- Authentication and authorization

The application supports two roles:

- **ADMIN:** Can view, add, update, and delete employees and departments.
- **USER:** Can view employees and departments but cannot perform create, update, or delete operations.

The main objective of this project is to understand how a React frontend communicates with a secure Spring Boot backend and how role-based access control is implemented in a full-stack application.

---

## Technologies Used

### Frontend

| Technology | Purpose |
|---|---|
| React.js | Frontend development |
| JavaScript (ES6+) | Application logic |
| HTML5 | Page structure |
| CSS3 | Styling and responsive UI |
| Fetch API | Backend API communication |
| Session Storage | JWT token storage |

### Backend

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
| JUnit | Testing |

### Development Tools

- IntelliJ IDEA
- Visual Studio Code
- Postman
- Git and GitHub

---

## Application Architecture

The application follows a full-stack architecture with separate frontend and backend layers.

```text
                  CLIENT
                    |
                    |
          ┌──────────────────┐
          │   React Frontend │
          │                  │
          │  Login Page      │
          │  Admin Dashboard │
          │  User Dashboard  │
          │  Employee UI     │
          │  Department UI   │
          └──────────────────┘
                    |
                    | HTTP Requests
                    | JWT Authorization Header
                    |
          ┌──────────────────┐
          │ Spring Boot API  │
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │ Spring Security  │
          │ JWT Authentication│
          │ Role Authorization│
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │   Controllers    │
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │    Services      │
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │   Repositories   │
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │  JPA / Hibernate │
          └──────────────────┘
                    |
                    ↓
          ┌──────────────────┐
          │      MySQL       │
          └──────────────────┘
```

---

## Frontend Architecture

The frontend is developed using React.js and communicates with the Spring Boot backend through REST APIs.

The frontend is organized into reusable components, pages, and service modules.

### Frontend Structure

```text
frontend/
│
├── src/
│   │
│   ├── components/
│   │   ├── Sidebar.jsx
│   │   ├── Navbar.jsx
│   │   └── SummaryCard.jsx
│   │
│   ├── pages/
│   │   ├── Login.jsx
│   │   ├── Dashboard.jsx
│   │   ├── Employees.jsx
│   │   └── Departments.jsx
│   │
│   ├── services/
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── employeeService.js
│   │   └── departmentService.js
│   │
│   ├── App.jsx
│   ├── App.css
│   └── index.css
│
├── package.json
└── vite.config.js
```

### Frontend Responsibilities

- Provides a login interface for users.
- Displays role-specific dashboards.
- Communicates with backend REST APIs.
- Displays employee and department information.
- Provides forms for adding and updating records.
- Displays dashboard statistics.
- Handles JWT token storage and authenticated API requests.
- Controls the visibility of administrative actions based on user roles.

---

## Role-Based Access Control

The application implements role-based access control using Spring Security and JWT authentication.

| Functionality | ADMIN | USER |
|---|:---:|:---:|
| Login | Yes | Yes |
| View Dashboard | Yes | Yes |
| View Employees | Yes | Yes |
| Add Employees | Yes | No |
| Update Employees | Yes | No |
| Delete Employees | Yes | No |
| View Departments | Yes | Yes |
| Add Departments | Yes | No |
| Update Departments | Yes | No |
| Delete Departments | Yes | No |

### ADMIN Dashboard

The administrator has complete access to employee and department management.

Features:
- View total employee count.
- View total department count.
- Add new employees.
- Update employee details.
- Delete employees.
- Add new departments.
- Update department details.
- Delete departments.

### USER Dashboard

Regular users have read-only access.

Features:
- View dashboard statistics.
- View employee details.
- View department details.
- Access employee and department lists without administrative modification options.

**Security Note:** The frontend hides administrative actions for USER accounts, while Spring Security enforces authorization at the backend API level.

---

## Backend Architecture

The backend follows a layered architecture that separates responsibilities between different components.

### Layers

1. **Controller Layer:** Handles incoming HTTP requests and returns appropriate responses.
2. **Service Layer:** Contains business logic and application operations.
3. **Repository Layer:** Handles database operations using Spring Data JPA.
4. **Entity Layer:** Defines database entities and relationships.
5. **DTO Layer:** Transfers request and response data between frontend and backend.
6. **Security Layer:** Handles JWT authentication and role-based authorization.
7. **Exception Handling:** Manages application exceptions and error responses.
8. **Configuration Layer:** Contains application and security configurations.

### Entity Relationships

- Employee and Department have a Many-to-One relationship.
- Multiple employees can belong to the same department.
- Each employee is associated with a department through `department_id`.

---

## Authentication and Authorization Flow

1. User enters username and password in the React login page.
2. React sends login credentials to the Spring Boot authentication endpoint.
3. Spring Security authenticates the user.
4. The backend generates a JWT containing the user's role.
5. React receives the token and stores it in Session Storage.
6. Subsequent API requests include the JWT in the Authorization header.
7. The JWT authentication filter validates the token.
8. Spring Security checks the user's role before allowing access to protected endpoints.
9. The backend returns the requested data to the React frontend.

### JWT Request Header

```http
Authorization: Bearer <JWT_TOKEN>
```

---

## REST API Endpoints

### Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/login` | Authenticate user and generate JWT |

### Employee APIs

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/employees` | Create employee | ADMIN |
| GET | `/employees` | Get all employees | ADMIN, USER |
| GET | `/employees/{id}` | Get employee by ID | ADMIN, USER |
| PUT | `/employees/{id}` | Update employee | ADMIN |
| DELETE | `/employees/{id}` | Delete employee | ADMIN |

### Department APIs

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/departments` | Create department | ADMIN |
| GET | `/departments` | Get all departments | ADMIN, USER |
| GET | `/departments/{id}` | Get department by ID | ADMIN, USER |
| PUT | `/departments/{id}` | Update department | ADMIN |
| DELETE | `/departments/{id}` | Delete department | ADMIN |

---

## Database Design

The application uses MySQL for relational data persistence.

Main entities include:

- Employee
- Department
- User
- Role

The application uses Spring Data JPA and Hibernate to map Java entities to database tables and manage relationships.

---

## How to Run the Project

### Prerequisites

- Java 17 or compatible project-configured JDK
- Node.js and npm
- MySQL
- Maven
- IntelliJ IDEA or VS Code

### 1. Clone the Repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
cd Employee-Management-System
```

### 2. Configure MySQL

Create the database:

```sql
CREATE DATABASE employee_management;
```

Configure your database credentials in:

`src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

Configure the remaining application properties, including JWT secret and JPA settings, as required by your backend.

### 3. Run the Spring Boot Backend

```bash
mvn clean install
mvn spring-boot:run
```

Backend runs at:

```text
http://localhost:8080
```

### 4. Run the React Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

Frontend runs at:

```text
http://localhost:5173
```

---

## Key Learning Outcomes

Through this project, I gained practical experience in:

- Developing RESTful APIs using Spring Boot.
- Implementing layered architecture.
- Performing CRUD operations using Spring Data JPA.
- Designing entity relationships.
- Integrating MySQL with Spring Boot.
- Implementing JWT-based authentication.
- Implementing role-based authorization using Spring Security.
- Building reusable frontend components using React.js.
- Integrating React with Spring Boot REST APIs.
- Managing authentication tokens in the frontend.
- Handling API requests, responses, and errors.
- Implementing role-based dashboards for ADMIN and USER.

---

## Future Enhancements

- Implement pagination and sorting.
- Add advanced employee search and filtering.
- Improve dashboard analytics.
- Add automated integration testing.
- Containerize the application using Docker.
- Deploy the frontend and backend to cloud infrastructure.

---

## Conclusion

The Employee Management System demonstrates a full-stack application integrating React.js with Spring Boot and MySQL.

It provides secure authentication, role-based access control, employee and department management, and a structured architecture that separates frontend responsibilities from backend business logic.

The project helped develop practical skills in full-stack application development, REST API integration, database management, and application security.
