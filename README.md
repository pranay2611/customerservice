# Customer Service
Banking Customer Service for managing customers.

## Author & Repository
### Name
Pranay Kumar Biswas

### BITS ID
2024TM93042

### GitHub repository
https://github.com/pranay2611/customerservice

## Features
- CRUD operations on Customer
- REST API endpoints
- PostgreSQL database
- Swagger API documentation
- RabbitMQ asynchronous messaging during customer creation & updation


## Tech Stack
- Java 21
- Spring Boot 3.1.4
- PostgreSQL
- RabbitMQ
- Maven

## Prerequisites
- Java 21+
- Maven 3.9+
- PostgreSQL 14+
- RabbitMQ 3.12+

## Setup Instructions
### 1. Database Setup (if not using docker-compose)
CREATE DATABASE customers;

CREATE ROLE customer WITH LOGIN PASSWORD 'customer';

CREATE DATABASE customers OWNER customer;

GRANT ALL PRIVILEGES ON DATABASE customers TO customer;

### 2. Configure Application
Edit `src/main/resources/application.properties`:
- Set PostgreSQL password
- Set Gmail credentials
- Configure RabbitMQ (default: localhost:5672)

### 3. Build and Run
**For local setup, run:**

mvn clean install  
mvn spring-boot:run

**Use docker-compose for containerized setup**

**Build the Project JAR:**
mvn clean package -DskipTests

**Run:** docker-compose up --build

### Launch Supporting Services with Docker Compose
Either start RabbitMQ locally, or
If you have a docker-compose.yml with RabbitMQ containers configured, run it (it will require application.properties endpoint changes).

Check containers with: docker ps

### Access Points
- Application: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- Health Check: http://localhost:8080/api/health


## API Endpoints
### Customer
- POST `/api/customers` - Create new customer
- GET `/api/customers` - Retrieve all customers
- GET `/api/customers/{id}` - Retrieve customer by ID
- PUT `/api/customers/{id}` - Update customer by ID
- DELETE `/api/customers/{id}` - Delete customer by ID


## RabbitMQ Queues
### Events Publishing On:
- `customer.update` - Customer create/update events


## Test the API
Open http://localhost:8080/swagger-ui.html in your browser, or use Postman to send requests to your endpoints.
