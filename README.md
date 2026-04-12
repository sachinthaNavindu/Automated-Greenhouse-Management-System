#  Automated Greenhouse Management System (AGMS)

## Microservice-Based Application

This project implements a cloud-native **Automated Greenhouse Management System (AGMS)** using a microservice architecture. The system integrates with a live external IoT data provider to fetch real-time temperature and humidity telemetry, applies rule-based automation logic, and manages crop inventory lifecycles.

---

## 📌 Table of Contents

- Architecture Overview  
- Technologies Used  
- Prerequisites  
- Step-by-Step Startup Guide  
- API Endpoints Summary  
- Testing with Postman  
- Eureka Dashboard  
- Project Structure  
- Troubleshooting  

---

## 🏗️ Architecture Overview

| Service | Port | Description |
|--------|------|-------------|
| Config Server | 8888 | Centralized configuration management (Git-backed) |
| Service Registry (Eureka) | 8761 | Dynamic service discovery |
| API Gateway | 8080 | Single entry point + JWT security |
| Zone Management Service | 8081 | Manage greenhouse zones |
| Sensor Telemetry Service | 8082 | Fetch IoT data every 10 seconds |
| Automation Service | 8083 | Rule engine (Fan/Heater) |
| Crop Inventory Service | 8084 | Plant lifecycle tracking |

---

## ⚙️ Technologies Used

- Spring Boot  
- Spring Cloud Gateway  
- Spring Cloud Eureka  
- Spring Cloud Config  
- OpenFeign  
- JWT Authentication  
- External IoT API  

---

## 📋 Prerequisites

- Java 17+  
- Maven  
- Git  
- Postman  
- Internet connection  

---

## 🚀 Step-by-Step Startup Guide

> ⚠️ Start infrastructure services BEFORE microservices

---

### 1️⃣ Start Infrastructure Services

Open 3 terminals:

#### Config Server
```bash
cd config-server
mvn spring-boot:run
```

#### Eureka Server
```bash
cd service-registry
mvn spring-boot:run
```
Dashboard → http://localhost:8761

#### API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
Gateway → http://localhost:8080

---

### 2️⃣ Start Domain Microservices

Run each in a new terminal:

```bash
cd zone-service
mvn spring-boot:run
```

```bash
cd sensor-service
mvn spring-boot:run
```

```bash
cd automation-service
mvn spring-boot:run
```

```bash
cd crop-service
mvn spring-boot:run
```

---

### 3️⃣ External IoT API Authentication

#### Register
```bash
POST http://104.211.95.241:8080/api/auth/register
Content-Type: application/json

{
  "username": "greenhouse_farmer",
  "password": "secure123"
}
```

#### Login
```bash
POST http://104.211.95.241:8080/api/auth/login
Content-Type: application/json

{
  "username": "greenhouse_farmer",
  "password": "secure123"
}
```

#### Response
```json
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc..."
}
```

Sensor service will:
- Auto authenticate  
- Refresh tokens  
- Fetch data every 10 seconds  

---

## 📡 API Endpoints Summary

Base URL:
```
http://localhost:8080
```

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/zones | Create zone |
| GET | /api/zones/{id} | Get zone |
| PUT | /api/zones/{id} | Update zone |
| DELETE | /api/zones/{id} | Delete zone |
| GET | /api/sensors/latest | Latest telemetry |
| GET | /api/automation/logs | Automation logs |
| POST | /api/crops | Add crop |
| PUT | /api/crops/{id}/status | Update crop |
| GET | /api/crops | List crops |


---


## 🖥️ Eureka Dashboard

Open:
```
http://localhost:8761
```

Services should show:
- API-GATEWAY  
- ZONE-SERVICE  
- SENSOR-SERVICE  
- AUTOMATION-SERVICE  
- CROP-SERVICE  

Screenshot:
```
docs/eureka-dashboard.png
```

---

## 📁 Project Structure

```
agms/
├── config-server/
├── service-registry/
├── api-gateway/
├── zone-service/
├── sensor-service/
├── automation-service/
├── crop-service/
├── docs/
│   └── eureka-dashboard.png
└── AGMS_Postman_Collection.json
```

Screenshots - ``` ```
