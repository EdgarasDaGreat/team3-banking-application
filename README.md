# Demo Banking Application — Team 3

A Spring Boot REST API for a simple demo bank: create accounts, look them up, deposit and withdraw funds, list their transactions, and transfer money between them. Responses are **JSON only** (no UI), and data is held in **in-memory storage** (no database).

**Team 3**
- Edgaras Daugevičius
- Karyna Yatsenko
- Matthew Harris
- Raivis Priede

---

## Project status

| Area | Status |
|------|--------|
| Project setup (Spring Web, Validation, Lombok) | ✅ Done |
| Domain model (`User`, `Account`, `Transaction`, enums) | ✅ Done |
| DTOs (records) + input validation | ✅ Done |
| Service layer (`AccountService`, `TransactionService`, `TransferService`) | ✅ Done (interfaces + impls; not yet exposed via controllers) |
| REST controllers / endpoints | 🚧 In progress |
| CI/CD deploy (GitHub Actions + Docker) | ✅ Working |

The API described below is the **target design** from the assignment; some endpoints are still being implemented.

---

## Tech stack

- **Java 17**
- **Spring Boot 3.2.3** — `spring-boot-starter-web`
- **Bean Validation** — `spring-boot-starter-validation` (Hibernate Validator)
- **Lombok** — boilerplate (getters/setters/builders)
- **Spring Boot DevTools** — hot reload during development
- **Maven** — build tool (wrapper included)

> Note: `spring-boot-starter-thymeleaf` is still listed in `pom.xml` but is **no longer used** (the demo UI was removed). It can be deleted.

---

## Prerequisites

- **Java 17+** — check with `java -version`
- **Git**
- **Maven** is optional — the repo ships a Maven wrapper (`./mvnw`) that downloads its own Maven. A system Maven 3.8+ also works.

---

## Running the application

### Option A — Maven wrapper (no local Maven needed)

The wrapper downloads a pinned Maven (3.9.6) into `.maven/` on first run, then builds/runs the app.

**macOS / Linux:**
```bash
# If you get "permission denied", make it executable once:
chmod +x mvnw

./mvnw clean package                 # build the jar
./mvnw spring-boot:run               # run directly (hot reload)
```

**Windows:**
```bat
mvnw.cmd clean package
mvnw.cmd spring-boot:run
```

> The wrapper prefers `curl` and falls back to `wget` (macOS/Linux) or PowerShell (Windows), so it works out of the box on a stock machine. It needs internet access on first run to fetch Maven.

### Option B — System Maven

```bash
mvn clean package
mvn spring-boot:run
```

### Option C — Run the built jar

```bash
java -jar target/acn-bootcamp-app-1.0.0.jar
```

The app starts on **port 3300** by default.

### Changing the port

```bash
# Via flag
java -jar target/acn-bootcamp-app-1.0.0.jar --server.port=8080

# Via environment variable
SERVER_PORT=8080 java -jar target/acn-bootcamp-app-1.0.0.jar
```

---

## Domain model

| Entity | Fields |
|--------|--------|
| `User` | `userId` (UUID), `username`, `passwordHash`, `role` |
| `Account` | `accountId` (UUID), `iban`, `ownerName`, `balance` (`BigDecimal`) |
| `Transaction` | `transactionId` (UUID), `account`, `type`, `amount` (`BigDecimal`), `createdAt` (`Instant`), `note` |

Enums: `Role { USER, ADMIN }`, `TransactionType { DEPOSIT, WITHDRAWAL, TRANSFER }`.

---

## Project structure

```
src/main/java/com/example/acnbootcamp/
├── Application.java          # Spring Boot entry point (keep this class name — see Deployment)
├── domain/                   # Entities: User, Account, Transaction, Role, TransactionType
├── dto/
│   ├── request/              # Incoming payloads as records (+ validation annotations)
│   └── response/             # Outgoing JSON views as records
├── mapper/                   # domain <-> dto conversion
├── repository/               # In-memory storage (Map/List)
├── service/                  # Business logic interfaces: AccountService, TransactionService, TransferService
│   └── impl/                 # Service implementations (@Service)
├── controller/               # REST endpoints (@RestController)
└── exception/                # Custom exceptions + @RestControllerAdvice (JSON errors)

src/main/resources/
└── application.properties    # Config (server.port, app name)
```

---

## Deployment

Deployment is automated via GitHub Actions (`.github/workflows/deploy.yml`) to a **self-hosted runner**:

1. Triggers on push to `main` or `develop` (or manually via *workflow_dispatch*).
2. Builds the jar with Maven (`mvn clean package -DskipTests`).
3. Builds a Docker image (`Dockerfile`) and runs it, publishing `SERVER_PORT` (default **3300**).
4. **Health check**: waits until the container log prints `Started Application in …`, then reports success.

See [`docs/RUNNER_SETUP.md`](docs/RUNNER_SETUP.md) for runner configuration and [`docs/QUICK_START.md`](docs/QUICK_START.md) for a quick start.

> ⚠️ The deploy health check greps the startup log for `"Started Application in"`, which contains the **main class name**. Do **not** rename `Application` (e.g. to `BankingApplication`) without also updating the check in `deploy.yml`.

---

## Troubleshooting — app won't start

**Port already in use** (`Web server failed to start. Port 3300 was already in use.`)
```bash
lsof -i :3300            # find what's using it (macOS/Linux)
kill $(lsof -ti:3300)    # free it
# or just run on another port:
SERVER_PORT=8080 java -jar target/acn-bootcamp-app-1.0.0.jar
```

**Wrong Java version** (`UnsupportedClassVersionError`, or build fails)
```bash
java -version            # must be 17+
echo $JAVA_HOME          # point this at a JDK 17+ if needed
```

**`./mvnw: permission denied`**
```bash
chmod +x mvnw
```

**`wget: command not found` / wrapper can't download Maven**
- Make sure you're on the latest code (the wrapper now falls back to `curl`).
- No internet / behind a proxy? Skip the wrapper and use a system Maven: `mvn clean package`.

**Lombok errors in the IDE** (`cannot find symbol: method getBalance()`)
- Enable **annotation processing** in your IDE and install the **Lombok plugin** (IntelliJ IDEA / Eclipse). Command-line builds work without any plugin.

**Stale build / weird compile errors**
```bash
./mvnw clean package     # wipe target/ and rebuild
```

**Validation not triggering / no 400 on bad input**
- Ensure the controller parameter is annotated `@Valid`, and `spring-boot-starter-validation` is on the classpath (it is, in `pom.xml`).

## Troubleshooting — deployment

**Container starts then the workflow fails at "Verify deployment"**
- The health check looks for `Started Application in` in the logs. Check the real cause:
  ```bash
  docker logs acn-bootcamp-app
  ```
- If you renamed the main class, update the grep string in `deploy.yml`.

**GitHub Actions job never picks up**
- Verify the self-hosted runner is online: repo → Settings → Actions → Runners.
- Ensure the runner has Java 17+ and Docker available (`java -version`, `docker version`).

---