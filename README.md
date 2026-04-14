# Staff Management System

A full-stack Staff Management System built with Angular, Spring Boot, PostgreSQL, and Keycloak for authentication.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | Angular 17 |
| Backend | Spring Boot 3.5 (Java 17) |
| Database | PostgreSQL 16 |
| Auth | Keycloak 24 |
| Container | Docker + Docker Compose |

---

## Prerequisites

You only need **one thing** installed:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (Windows/Mac/Linux)

That's it. No need to install Java, Node.js, PostgreSQL, or Keycloak manually.

---

## How to Run

### Step 1 — Clone the repository

```bash
git clone <your-repo-url>
cd staff-springboot-angular-keycloak
```

### Step 2 — Start everything

```bash
docker-compose up --build
```

First time will take **5–10 minutes** as Docker downloads images and builds the project.
After that, subsequent runs will be much faster.

### Step 3 — Open the app

Once you see these lines in the terminal:
