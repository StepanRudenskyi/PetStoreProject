# Pet Store Application

## Overview

This is a Spring Boot-based web application that allows users to browse products, add them to a shopping cart, and
complete purchases. Users must authenticate before checking out. The application includes role-based access control
(RBAC) with `admin` and `user` roles.

## Features

- User registration and authentication
- Product browsing
- Shopping cart management
- Checkout process
- Auto cart restoration for items in the cart for more than 1 day
- Role-based access control (RBAC) with admin and user roles
- Admin capabilities for product management (future improvement)

## Technologies Used

- **Backend:** Spring Boot, Spring Security, Spring Data JPA
- **Frontend:** Thymeleaf
- **Database:** MariaDB
- **Build Tool:** Maven
- **ORM:** Hibernate
- **Validation:** Spring Boot Validation
- **Mapping:** MapStruct
- **Testing:** JUnit (minimal usage)
- **Deployment:** Docker, Docker Compose

## Setup & Installation

### Prerequisites

- Java 17+
- Maven
- Docker & Docker Compose

### Running Locally (Without Docker)

1. Clone the repository:
   ```sh
   git clone https://github.com/StepanRudenskyi/PetStoreProject.git
   ```
2. Set up the database:

- Ensure MariaDB is running (locally or through Docker).
- Create the database PETSTORE in MariaDB (this can be done through the SQL script, which is explained 
[here](#sql-initialization-script)).

3. Configure the database in `application.properties`: 

   Create a .env file in the root directory with the following variables (this is just a sample; adjust as needed):
   ```properties
   SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/PETSTORE
   SPRING_DATASOURCE_USERNAME=root
   SPRING_DATASOURCE_PASSWORD=password
   ```
4. Build and run the application:
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```
5. The application should be accessible at `http://localhost:8080`

### Running with Docker

1. Build and start the application using Docker Compose:
   ```sh
   docker-compose up --build
   ```
2. The application will be available at `http://localhost:8080`

## SQL Initialization Script

The database schema and initial data are set up using the SQL initialization scripts located in the 
[`src/main/resources/sql_init`](src/main/resources/sql_init) directory. These scripts should be run before starting the application.

#### 1. [`ddl.sql`](src/main/resources/sql_init/ddl.sql) (Data Definition Language)

This script contains all the necessary SQL statements to **create** the database schema, including tables, 
foreign keys, and indexes. It is responsible for setting up the structure of the database.

#### 2. [`dml.sql`](src/main/resources/sql_init/dml.sql) (Data Manipulation Language)
This script contains SQL statements to **insert** initial data into the database, such as sample users, products, 
and other necessary records. It sets up the database with the required starting state for development or testing.
By default creates admin user with username: `admin` and password: `1234`

## Usage

- Register a new user
- Login using the credentials
- Browse products and add them to the cart
- Checkout after authentication
- Admin users (future feature) will be able to manage products

## Future Improvements & Scaling Considerations

- Implement product management for admins
- Improve test coverage with JUnit and integration tests
- Add caching for frequently accessed products
- Enhance security measures (e.g., OAuth, JWT authentication)
- Deploy on cloud platforms like AWS/GCP with managed databases