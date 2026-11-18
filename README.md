# Hospital Manager

A REST API for managing hospital patients, appointments, staff, and medical records. The application is built with Spring Boot, Spring Security, JPA/Hibernate, JWT authentication, and MySQL.

## Features

- JWT-based login and registration
- Role-based access control
- Patient registration, search, update, and deletion
- Appointment booking and status management
- Doctor-created medical records
- Staff and doctor account management
- Seeded users and a sample patient for local development

## Technology Stack

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Security
- Spring Data JPA
- JJWT 0.11.5
- MySQL
- Maven
- Lombok

## Prerequisites

- JDK 21
- Maven 3.9 or newer, or the included Maven Wrapper
- MySQL 8 or compatible MySQL server

## Database Configuration

The application expects a MySQL database named `hospital_management`. The database is created automatically when the MySQL account has permission to create databases.

Default values in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=
```

Set `spring.datasource.username` and `spring.datasource.password` for your MySQL installation.

## JWT Configuration

Set a JWT signing secret containing at least 32 UTF-8 bytes. On Windows PowerShell:

```powershell
$env:JWT_SECRET="replace-with-a-long-random-secret-value"
```

On Linux or macOS:

```bash
export JWT_SECRET="replace-with-a-long-random-secret-value"
```

The application will not start without `JWT_SECRET` and rejects secrets shorter than 32 bytes.

## Running the Application

1. Start MySQL.
2. Configure the database credentials and `JWT_SECRET`.
3. Run the application:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

The API is available at `http://localhost:8080`.

## Running Tests

```powershell
.\mvnw.cmd test
```

On Linux or macOS:

```bash
./mvnw test
```

## Authentication

Register a public account:

```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "newuser",
  "password": "Password123",
  "role": "PATIENT"
}
```

Log in:

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "newuser",
  "password": "Password123"
}
```

The login response contains a JWT. Send it with protected requests:

```http
Authorization: Bearer <jwt-token>
```

Use `/api/auth/me` to inspect the authenticated user.

## Seeded Development Accounts

The following accounts are created automatically when they do not already exist:

| Role | Username | Password |
|---|---|---|
| Admin | `admin` | `Admin12345` |
| Doctor | `doctor` | `Doctor12345` |
| Nurse | `nurse` | `Nurse12345` |
| Receptionist | `receptionist` | `Reception12345` |

Only use these credentials in local development.

## API Overview

### Authentication

- `POST /api/auth/register` - register a public user
- `POST /api/auth/login` - authenticate and receive a JWT
- `GET /api/auth/me` - get the authenticated user

### Users and Staff

- `GET /api/users` - list users; admin only
- `GET /api/doctors` - list doctors; admin, patient, or receptionist
- `POST /api/users` - create a staff user; admin only

### Patients

- `GET /api/patients` - list patients
- `GET /api/patients/{id}` - get a patient
- `GET /api/patients/search?name={name}` - search patients by name
- `POST /api/patients` - create a patient
- `PUT /api/patients/{id}` - update a patient
- `DELETE /api/patients/{id}` - delete a patient; admin only

Patient operations are restricted to authorized roles as configured in the controllers.

### Appointments

- `POST /api/appointments` - book an appointment
- `GET /api/appointments` - list appointments
- `GET /api/appointments/patient/{patientId}` - list a patient's appointments
- `PATCH /api/appointments/{id}/status` - update appointment status

### Medical Records

- `POST /api/records` - create a medical record; doctor only
- `GET /api/records/patient/{patientId}` - get a patient's records
- `GET /api/records` - list all records; admin only

## Configuration Reference

| Property | Purpose |
|---|---|
| `server.port` | HTTP server port; defaults to `8080` |
| `spring.datasource.url` | MySQL JDBC URL |
| `spring.datasource.username` | MySQL username |
| `spring.datasource.password` | MySQL password |
| `spring.jpa.hibernate.ddl-auto` | Hibernate schema behavior; defaults to `update` |
| `app.jwt.secret` | JWT signing secret from `JWT_SECRET` |
| `app.jwt.expiration-ms` | JWT lifetime in milliseconds; defaults to `3600000` |

