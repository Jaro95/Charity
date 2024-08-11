
# REST API for Charity Donation Application

## Project Description

The Charity Donation REST API is a web service designed to facilitate the donation of various items to selected foundations. It provides endpoints for managing donations, user accounts, and foundations. This API allows external applications to interact with the service using HTTP requests.

## Features

### User Features
- **User Registration**: Create a new user account with email confirmation.
- **Donate Items**: Allow users to donate items to selected foundations.
- **User Account Management**: Manage user accounts with CRUD operations.
- **Donation Management**: Manage donations with CRUD operations, including marking them as received.

### Admin Features
- **Admin Management**: Manage admin accounts with CRUD operations.
- **User Management**: Admins can manage user accounts with CRUD operations.
- **Donation Management**: Full control over donations, including viewing, updating, and deleting.
- **Category Management**: Manage donation categories with CRUD operations.
- **Foundation Management**: Manage foundations with CRUD operations.

## Technologies Used

- **Spring Boot 3+**: Backend framework for creating the REST API.
- **Java 17+**: Programming language used for development.
- **Spring Security**: For authentication and authorization.
- **Spring Data JPA**: For data persistence.
- **MySQL**: Database system used for storing data.
- **Flyway**: For database migration.
- **Docker**: For containerizing the application and database.
- **Spring Boot Mail**: For email functionalities.
- **Hibernate**: ORM framework for database operations.

## Installation

### Prerequisites

- **Docker**: Ensure Docker is installed and running on your machine.
- **MySQL**: Ensure MySQL is installed and accessible via Docker or directly on your machine.

### Clone the Repository

```bash
git clone https://github.com/Jaro95/Charity.git
cd charity
```
## Configure the Application

Edit the `application.properties` file to configure the database and mail settings:

```properties
# MySQL Database Configuration
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB_NAME:charity}?serverTimezone=UTC
spring.datasource.username=${MYSQL_USER:root}
spring.datasource.password=${MYSQL_PASSWORD:root}
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
## Run the Application

1. **Start MySQL Database**: Use Docker to run the MySQL container. Example Docker command:

   ```bash
   docker run --name mysql-charity -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=charity -p 3306:3306 -d mysql:latest
   ```
## API Documentation

### Base URL
http://localhost:8080/api


You can view the API documentation using Swagger Editor:

- [Swagger Editor with API Documentation](https://editor.swagger.io/?url=https://raw.githubusercontent.com/Jaro95/Charity/main/docs/swagger.json)

## Usage
- Testing Endpoints: Use tools like Postman or curl to test the API endpoints.
- Authentication: Use the JWT token obtained from the login endpoint for protected routes.