# 🎟️ Event Ticket Platform

A full-stack **event ticketing system** that allows organizers to create events and sell tickets, attendees to discover and purchase them, and staff to validate tickets using QR codes at event entry.

This platform manages the **entire event lifecycle** — from event creation to ticket validation and sales reporting.

---

## Table Of Contents

- [Frontend Installation](#-run-the-frontend)
- [Backend Installation](#-run-the-backend)

## 🚀 What this platform does

The Event Ticket Platform supports three types of users:

### 👩‍💼 Organizers
- Create and manage events  
- Define ticket types (VIP, Standard, etc.)  
- Set ticket prices and availability  

### 🧑‍🤝‍🧑 Attendees
- Browse and search events  
- View event details  
- Buy tickets securely  
- Receive digital tickets with QR codes  

### 🛂 Event Staff
- Scan QR codes at event entry  
- Validate ticket authenticity  
- Prevent duplicate or invalid ticket usage  
- Manually enter ticket numbers if scanning fails  

---

## 🧩 Core Features

### Event Management
- Create, update, and delete events  
- Set venue, date, time, and sales period  
- Publish and cancel events  

### Ticketing System
- Multiple ticket types per event  
- Ticket quantity limits  

### Ticket Purchase
- Browse published events  
- Select ticket types  
- Digital ticket generation  

### QR Code Validation
- Each ticket has a QR code  
- Staff scan QR codes at entry  
- System checks:
  - Validity
  - Duplicate usage
  - Expiration  
- Manual fallback if scanning fails  

## 🏗️ System Architecture

[ React Frontend ]
        |
        v
[ Spring Boot REST API ]
        |
        v
[ PostgreSQL Database ]
        |
        v
[ Keycloak Auth Server ]

### Components
- **Spring Boot** – Backend REST API  
- **React** – Frontend UI  
- **PostgreSQL** – Relational database  
- **Keycloak** – Authentication & Authorization (OAuth2 / OpenID Connect)

---

## 🛠️ Tech Stack

### Backend
- Java 21  
- Spring Boot  
- Spring Web  
- Spring Security  
- Spring Data JPA  
- PostgreSQL  
- MapStruct  
- Lombok  

### Frontend
- React  
- npm  

### Authentication
- Keycloak (OAuth2, JWT, OpenID Connect)

### DevOps
- Docker  
- Adminer  

---

## 🧬 Domain Model

| Entity | Purpose |
|------|--------|
| **User** | Represents organizers, attendees, and staff |
| **Event** | Stores event details (name, venue, dates, status) |
| **TicketType** | Ticket categories (VIP, Standard, price, availability) |
| **Ticket** | Represents a purchased ticket |
| **QrCode** | QR code for ticket entry |
| **TicketValidation** | Records ticket scan results |

---

## 🔐 Authentication & Roles

Authentication is handled by **Keycloak**.

Roles:
- `ORGANIZER`
- `ATTENDEE`
- `STAFF`

Spring Boot validates JWT tokens issued by Keycloak.

---

## 🌐 REST API Overview

### Organizer

- POST /api/v1/events
- GET /api/v1/events
- PUT /api/v1/events/{id}
- DELETE /api/v1/events/{id}

- GET /api/v1/events/{id}/ticket-types
- PATCH /api/v1/events/{id}/ticket-types
- GET /api/v1/events/{id}/tickets

### Attendee

- GET /api/v1/published-events
- GET /api/v1/published-events/{id}
- POST /api/v1/published-events/{id}/ticket-types/{ticketTypeId}

- GET /api/v1/tickets
- GET /api/v1/tickets/{id}
- GET /api/v1/tickets/{id}/qr-codes

### Staff

- POST /api/v1/events/{id}/ticket-validations
- GET /api/v1/events/{id}/ticket-validations

---

## 🐳 Run the Frontend

### 1️⃣ Install required dependencies

```bash
npm install --force
npm run dev
```

### Frontend will run at:
```bash
http://localhost:5173
```

## 🐳 Run the Backend

### 1️⃣ Start PostgreSQL & Keycloak

```bash
docker compose up
```
### This Starts:
- PostgreSQL → 5432
- Adminer → 8888
- Keycloak → 9090

### 3️⃣ Configure Backend

```bash
mvn clean install 
mvn clean compile
```

### 3️⃣ Run Backend

```bash
mvn spring-boot:run
```

