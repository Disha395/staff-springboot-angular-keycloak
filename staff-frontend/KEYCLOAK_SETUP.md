# Keycloak Setup Guide — Staff Management System

## Step 1: Run Keycloak in Docker

```bash
docker run -d \
  --name keycloak \
  -p 8080:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:24.0.1 \
  start-dev
```

Wait ~30 seconds then open: http://localhost:8080
Login with: admin / admin

---

## Step 2: Create the Realm

1. In the top-left dropdown (it says "Keycloak"), click **Create realm**
2. Set **Realm name** = `staff-realm`
3. Click **Create**

You are now inside the `staff-realm`.

---

## Step 3: Create the Client (for Angular frontend)

1. Go to **Clients** → **Create client**
2. Fill in:
   - **Client type**: OpenID Connect
   - **Client ID**: `staff-frontend-client`
   - Click **Next**
3. On the **Capability config** page:
   - Turn ON: **Standard flow** (Authorization Code)
   - Turn OFF: Client authentication (keep it PUBLIC — Angular is a public client)
   - Click **Next**
4. On the **Login settings** page:
   - **Root URL**: `http://localhost:4200`
   - **Valid redirect URIs**: `http://localhost:4200/*`
   - **Valid post logout redirect URIs**: `http://localhost:4200/*`
   - **Web origins**: `http://localhost:4200`
   - Click **Save**

---

## Step 4: Enable User Registration in the Realm

1. Go to **Realm settings** → **Login** tab
2. Turn ON:
   - **User registration** ✅
   - **Email as username** — optional, leave OFF to use a separate username
   - **Remember me** ✅
   - **Login with email** ✅
3. Click **Save**

This allows anyone to self-register directly from the Keycloak login page.

---

## Step 5: Configure Token Settings (optional but recommended)

1. Go to **Realm settings** → **Tokens** tab
2. Set:
   - **Access Token Lifespan**: 15 minutes
   - **SSO Session Idle**: 30 minutes
3. Click **Save**

---

## Step 6: Verify it works end-to-end

1. Start Keycloak (Step 1 above)
2. Start PostgreSQL and the Spring Boot backend:
   ```bash
   cd management
   ./mvnw spring-boot:run
   ```
3. Start the Angular frontend:
   ```bash
   cd staff-frontend
   npm install
   ng serve
   ```
4. Open http://localhost:4200
5. You will be redirected to the Keycloak login page
6. Click **Register** to create a new account, fill in your details
7. After registering, you are automatically logged in and redirected to the dashboard
8. Your JWT token is automatically attached to all API calls to Spring Boot

---

## How it all works (summary)

```
User Browser (Angular :4200)
        |
        | 1. Not logged in → redirect to Keycloak
        ▼
Keycloak (:8080/realms/staff-realm)
        |
        | 2. User logs in / registers
        | 3. Keycloak issues JWT access token
        ▼
Angular app receives token, stores in memory
        |
        | 4. Every API call adds:  Authorization: Bearer <token>
        ▼
Spring Boot (:8081/api/staff)
        |
        | 5. Spring validates JWT signature using Keycloak's public key
        | 6. If valid → process request, return data
        ▼
Angular displays response
```

---

## Ports summary

| Service        | Port |
|----------------|------|
| Keycloak       | 8080 |
| Spring Boot    | 8081 |
| Angular        | 4200 |
| PostgreSQL     | 5432 |

---

## Troubleshooting

**"CORS error on Keycloak"**
→ Make sure Web origins is set to `http://localhost:4200` in the client settings (Step 3)

**"keycloak.js not loading"**
→ Keycloak must be running on port 8080 BEFORE you open the Angular app. The script tag in index.html loads it from `http://localhost:8080/js/keycloak.js`

**"401 Unauthorized from Spring Boot"**
→ Check that `spring.security.oauth2.resourceserver.jwt.issuer-uri` in application.properties matches your realm URL exactly: `http://localhost:8080/realms/staff-realm`

**"Token expired"**
→ The Angular app auto-refreshes the token every 60 seconds. If you stay idle for > 30 min (SSO idle timeout), you'll be redirected to login again.

**Want to see user details in Spring Boot logs?**
→ The JWT contains `preferred_username`, `email`, `sub` (user ID). You can inject the `Authentication` object in any controller method to read them.
