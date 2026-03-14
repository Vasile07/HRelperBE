# HRelper Backend

## Overview

**HRelperBE** is the backend service for the **[HRelper](https://github.com/Vasile07/HRelper)** application.
It is built using **Java**, **Spring Boot**, and **Gradle**, and exposes a REST API that can be consumed by the HRelper frontend. The project is part of the broader HRelper ecosystem, which also includes the public landing page that presents the platform and its features.

The backend is responsible for:

* Business logic
* Authentication and security
* Data persistence
* API endpoints for the frontend

The project is structured as a **multi-module Gradle project** to keep responsibilities separated and maintain clean architecture.

---

# Project Architecture

The backend is organized into multiple modules:

```
HRelperBE
 ├ Model          → Domain entities and data models
 ├ Persistence    → Database repositories and data access
 ├ Business       → Business logic and service layer
 ├ Utils          → Security, configuration, and utilities
 └ RestController → REST API controllers and application entry point
```

**RestController** is the main Spring Boot application module responsible for exposing API endpoints.

---

# Technologies Used

* Java 21
* Spring Boot
* Gradle (multi-module build)
* PostgreSQL
* Supabase (database hosting)
* JWT Authentication
* OpenAPI / Swagger for API documentation

---

# Local Development

## 1. Clone the repository

```
git clone https://github.com/<your-username>/HRelperBE.git
cd HRelperBE
```

---

## 2. Build the project

```
./gradlew build
```

or on Windows:

```
gradlew build
```

---

## 3. Run the application

You can run the backend from the **RestController module**:

```
./gradlew :RestController:bootRun
```

The server will start on:

```
http://localhost:8080
```

---

# API Documentation (Swagger)

For local testing and exploration of the API, Swagger UI is enabled.

Open the following URL in your browser:

```
http://localhost:8080/swagger-ui/index.html#/
```

Swagger allows you to:

* Explore all available endpoints
* Test requests directly from the browser
* View request/response models
* Authenticate using JWT if required

---

# Deployed Backend

The backend is deployed publicly using **Render**.

Base API URL:

```
https://hrelperbe.onrender.com
```

Example endpoint:

```
https://hrelperbe.onrender.com/<endpoint>
```

This deployed instance is used by the production frontend.

---

# Database (Supabase)

The application uses a **PostgreSQL database hosted on Supabase**.

Supabase provides:

* Managed PostgreSQL database
* Secure connections
* Cloud-hosted data storage
* Scalable infrastructure

Spring Boot connects to Supabase using standard PostgreSQL JDBC configuration.

---

# API Security

The backend uses **JWT-based authentication** for securing protected endpoints.

Authentication flow:

1. User logs in with credentials.
2. Backend returns a JWT token.
3. Client sends the token in the `Authorization` header.
4. Spring Security validates the token for protected routes.

Example header:

```
Authorization: Bearer <token>
```

---

# Related Projects

This backend is part of the **HRelper ecosystem**:

* **[HRelper](https://github.com/Vasile07/HRelper)** – Landing page and project overview
* **[HRelperFE](https://github.com/Vasile07/HRelperFE)** – React frontend application
* **[HRelperBE](https://github.com/Vasile07/HRelperBE)** – Spring Boot backend API

---

# License

This project is part of an academic / learning project and is intended for educational purposes.
