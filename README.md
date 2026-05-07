# Staff Management System

A full-stack Staff Management System built with Angular, Spring Boot, PostgreSQL, and Keycloak for authentication.

---

``## Tech Stack

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
keycloak        | Running the server in development mode
spring-backend  | Started StaffManagementSystemApplication
angular-frontend | start worker process

Open your browser and go to:
http://localhost:4200

---

## First Time Usage

1. You will be redirected to the **Keycloak login page**
2. Click **Register** to create a new account
3. Fill in your details and submit
4. You are automatically logged in and taken to the dashboard

---

## Ports

| Service | URL |
|---|---|
| Angular App | http://localhost:4200 |
| Spring Boot API | http://localhost:8081 |
| Keycloak Admin | http://localhost:8080 |
| PostgreSQL | localhost:5432 |

---

## Keycloak Admin Panel

If you want to manage users manually:

1. Open http://localhost:8080
2. Login with `admin / admin`
3. Select the `staff-realm` from the top-left dropdown

---

## Stopping the App

```bash
# Stop all containers
docker-compose down

# Stop and delete all data (fresh start)
docker-compose down -v
```

---

## Project Structure
staff-springboot-angular-keycloak/
├── docker-compose.yml           # Runs everything together
├── keycloak-realm-export.json   # Auto-configures Keycloak realm
├── README.md
├── management/                  # Spring Boot backend
│   ├── Dockerfile
│   └── src/
└── staff-frontend/              # Angular frontend
├── Dockerfile
├── nginx.conf
└── src/



---

## Troubleshooting

**App not loading after `docker-compose up`**
→ Wait a full 2 minutes. Keycloak takes time to start. Refresh the page.

**`http://localhost:4200` shows blank page**
→ Keycloak JS might not be loaded yet. Hard refresh with `Ctrl + Shift + R`.

**`401 Unauthorized` errors in the app**
→ Your session expired. Log out and log back in.

**Want a completely fresh start (reset all data)**
```bash
docker-compose down -v
docker-compose up --build
```

**Port already in use error**
→ Something else is using port 8080, 8081, or 4200. Stop that process or change the port in `docker-compose.yml`.
