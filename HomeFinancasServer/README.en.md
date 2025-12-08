# HomeFinancasServer (springboot-clean-jwt-postgres)

Example Spring Boot project following Clean Architecture principles, with JWT authentication and PostgreSQL integration.

**Summary:** REST API for user management and JWT-based authentication. Ready to run with Docker (Postgres) or locally with Maven. Tests use an in-memory H2 database.

**Status:** Source code available under `src/`.

---

## Table of Contents
- **Overview**
- **Features**
- **Technology Stack**
- **Requirements**
- **Environment Variables**
- **Running (Docker)**
- **Running Locally (Maven)**
- **Building Docker Image**
- **Tests**
- **Main Endpoints**
- **Project Structure**
- **Security & Best Practices**
- **Contributing**
- **Contact / License**

---

## Overview

This project implements a small API for user management and authentication using a clean architecture approach. Authentication uses JWT (Bearer token). It is intended as a starting point for applications that require secure authentication and a clear separation of layers.

## Features

- User registration / creation (via the users endpoint)
- Authentication (login) that returns a JWT
- Endpoints protected by JWT
- Configuration to run with PostgreSQL via Docker Compose
- Tests configured with H2 for quick execution

## Technology Stack

- Java 17
- Spring Boot
- Maven (with wrapper `mvnw` / `mvnw.cmd`)
- PostgreSQL (via Docker Compose)
- JWT (implemented under `security/`)

## Requirements

- Java 17 installed
- Maven (optional if you use `./mvnw`)
- Docker and Docker Compose (recommended for development environment)

---

## Environment Variables

Copy `.env.exemple` to `.env` at the project root and fill in real values (do not commit `.env`).

Example (`.env`):

```dotenv
# Copy to .env and fill real secrets (do NOT commit .env)
DB_USER=user
DB_PASS=password
DB_NAME=database_name
JWT_SECRET=change-me-to-a-secure-secret
APP_PORT=8080

# Optional: change Postgres image / version
POSTGRES_IMAGE=postgres:15
```

These variables are used by `src/main/resources/application.yml`.

---

## Running with Docker Compose (recommended)

1. Copy `.env.exemple` to `.env` and update values.
2. Start services (app + database):

```powershell
docker compose up --build -d
```

3. The application will be available on the configured port (default `APP_PORT` = `8080`).

To stop and remove containers:

```powershell
docker compose down
```

---

## Running Locally (Maven)

1. Configure environment variables (or a local `application.yml`) pointing to an accessible Postgres instance.
2. Build and run:

```powershell
# Build
./mvnw clean package

# Run
./mvnw spring-boot:run
```

Alternatively, run the produced jar:

```powershell
java -jar target/springboot-clean-jwt-postgres-0.0.1-SNAPSHOT.jar
```

---

## Building Docker Image

To build the Docker image using the repository `Dockerfile`:

```powershell
docker build -t homefinancasserver:latest .
```

---

## Tests

Unit and integration tests are located under `src/test/java` and use H2 (configuration in `src/test/resources/application-test.yml`). To run tests:

```powershell
./mvnw test
```

Test reports are produced in `target/surefire-reports`.

---

## Main Endpoints

Base path: `/api`

- Authentication
  - `POST /api/auth/login` — Accepts an `AuthRequest` and returns an `AuthResponse` containing a JWT.
    - Example `AuthRequest` (JSON):
      ```json
      { "username": "user", "password": "password" }
      ```
    - Example response:
      ```json
      { "token": "<JWT>", "username": "user", "email": "user@example.com" }
      ```

- Users
  - `GET /api/users` — List all users (requires JWT Bearer token).
  - `GET /api/users/{id}` — Get a user by ID (requires JWT Bearer token).
  - `POST /api/users` — Create a new user (JSON body).

Note: JWT protection is implemented under the `security/` package. Include the header `Authorization: Bearer <token>` in requests to protected endpoints.

Quick example using PowerShell `curl`:

```powershell
# Authenticate and get token
$resp = curl -Method POST -Uri http://localhost:8080/api/auth/login -Body (@{username='user';password='password'} | ConvertTo-Json) -ContentType 'application/json'
$token = ($resp | ConvertFrom-Json).token

# Use token to list users
curl -Method GET -Uri http://localhost:8080/api/users -Headers @{ Authorization = "Bearer $token" }
```

---

## Project Structure (short)

- `src/main/java/com/example/cleanapp` — main source code
  - `adapters/inbound/web/controller` — REST controllers (`AuthController`, `UserController`)
  - `adapters/outbound/persistence` — entities, mappers and repositories
  - `application` — use cases (`service`, `port`, `dto`)
  - `domain` — domain models
  - `security` — filters, provider and JWT configuration
- `src/main/resources/application.yml` — runtime configuration
- `src/test` — unit and integration tests

---

## Security & Best Practices

- Never commit `.env` with real secrets.
- Use a strong `JWT_SECRET` and rotate it according to your security policy.
- In production, enable HTTPS and secure traffic between your app and the database.
- Never expose JWT tokens in URLs or logs.

---

## Contributing

1. Fork the repository
2. Create a branch for your feature or bugfix
3. Open a pull request describing your changes

---

## Contact / License

This project is provided as an example. See `pom.xml` for dependencies and version information.

License: MIT (see `LICENSE` if present)

---

File created: `README.en.md`.
