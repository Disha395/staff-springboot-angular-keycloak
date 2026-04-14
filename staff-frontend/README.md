# Staff Management System - Angular Frontend

## Prerequisites
- Node.js 18+ and npm
- Angular CLI 17: `npm install -g @angular/cli`
- Spring Boot backend running on port 8081

## Setup Instructions

### 1. Start the Backend
Make sure your Spring Boot app is running:
```
cd management
./mvnw spring-boot:run
```
Backend runs on: http://localhost:8081

### 2. Install Frontend Dependencies
```bash
cd staff-frontend
npm install
```

### 3. Run the Frontend
```bash
ng serve
```
Frontend runs on: http://localhost:4200

---

## Login Credentials (Demo)
- **Username:** admin
- **Password:** admin123

---

## Features
- **Login / Register** — simple auth with session storage
- **Dashboard** — stats overview (total staff, departments, avg salary, highest salary)
- **Staff Management** — full CRUD: Add, Edit, Delete, Search by name, Filter by dept/salary
- **Profiles** — card view of all staff, click to view details, search by ID

## API Endpoints Used
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/staff | Get all staff |
| GET | /api/staff/{id} | Get by ID |
| POST | /api/staff | Create staff |
| PUT | /api/staff/{id} | Update staff |
| DELETE | /api/staff/{id} | Delete staff |
| GET | /api/staff/department/{id} | Filter by dept |
| GET | /api/staff/salary/{min} | Filter by min salary |
| GET | /api/staff/search?name=... | Search by name |

## Project Structure
```
src/app/
├── core/
│   ├── models/staff.model.ts
│   ├── services/staff.service.ts
│   ├── services/auth.service.ts
│   └── guards/auth.guard.ts
├── auth/components/auth/
│   ├── login/
│   └── register/
├── dashboard/
├── staff/staff-list/
├── profiles/
├── app.component.*
├── app.module.ts
└── app-routing.module.ts
```
