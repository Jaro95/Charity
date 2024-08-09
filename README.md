
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
git clone https://github.com/your-username/charity-donation-api.git
cd charity-donation-api
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


### Endpoints

#### User Endpoints

- **Login**

  - **URL**: `/users/login`
  - **Method**: `POST`
  - **Description**: Log in a user and receive a JWT token.
  - **Body**: 
    ```json
    {
      "email": "example@example.com",
      "password": "password"
    }
    ```
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
        {
        "access_token": "yourToken",
        "refresh_token": "yourRefreshToken",
        "message": "User login was successful"
        }
      ```


- **Register User**

  - **URL**: `/users/register`
  - **Method**: `POST`
  - **Description**: Register a new user.
  - **Body**: 
    ```json
    {
      "email": "example@example.com",
      "password": "password"
    }
    ```
  - **Success Response**:
    - **Code**: `201 Created`
    - **Content**:
      ```json
      {
        "email" : "example@email.com",
        "firstName" : "name",
        "lastName" : "last name",
        "password" : "Password123!",
        "repeatPassword" : "Password123!"
      }
      ```

- **Verification Token User**

  - **URL**: `/users/verification`
  - **Method**: `GET`
  - **Description**: Verify the user's token for authenticity, then activate the user account.
  - **Body**: 
    ```json
    {
      "token": "exampleToken",
    }
    ```
  - **Success Response**:
    - **Code**: `200 Ok`
    - **Content**:
      ```json
      {
        "successful" : true,
        "message" : "The account has been activated",
        
      }
      ```

- **Get User Details**

  - **URL**: `/users/{id}`
  - **Method**: `GET`
  - **Description**: Retrieve details of a specific user.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "id": 1,
        "email": "example@example.com",
        "name": "vegeta",
        "lastName": "sayan",
        "enabled": true,
        "role": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
            }
        ],
        "token": "verified",
        "createdAccount": "2024-08-09T09:18:08"
      }
      ```
- **Get Users**

  - **URL**: `/users`
  - **Method**: `GET`
  - **Description**: Retrieve a list of all users.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "id": 1,
        "email": "example@example.com",
        "name": "vegeta",
        "lastName": "sayan",
        "enabled": true,
        "role": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
            }
        ],
        "token": "verified",
        "createdAccount": "2024-08-09T09:18:08"
      }
      ```

- **Get Users with role**

  - **URL**: `/users/role/{role}`
  - **Method**: `GET`
  - **Description**: Retrieve a list of all users with role.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "id": 1,
        "email": "example@example.com",
        "name": "vegeta",
        "lastName": "sayan",
        "enabled": true,
        "role": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
            }
        ],
        "token": "verified",
        "createdAccount": "2024-08-09T09:18:08"
      }
      ```

- **Send recovery password token**

  - **URL**: `/users/recovery/{email}`
  - **Method**: `POST`
  - **Description**: Sends a token to the specified email address with the option to change the password.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "successful": true,
        "message": "A password reset link has been sent to your email address",
      }
      ```
- **Check recovery password token**

  - **URL**: `/users/recovery/password`
  - **Method**: `GET`
  - **Description**: Authenticate the recovery password token to ensure its validity
  - **Body**: 
    ```json
    {
      "token": "yourToken"
    }
    ```
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "successful": true,
        "message": "Token is valid"
      }
      ```
- **Recovery password**

  - **URL**: `/users/recovery/password`
  - **Method**: `POST`
  - **Description**: Authenticate the recovery password token to ensure its validity and then reset the user's password accordingly.
  - **Body**: 
    ```json
    {
      "token": "yourToken",
      "password": "yourPassword",
      "repeatPassword": "yourPassword",
    }
    ```
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "successful": true,
        "message": "The password has been changed"
      }
      ```

- **Update user**

  - **URL**: `/users/{id}`
  - **Method**: `PUT`
  - **Description**: Update the user data by modifying the necessary fields in the database.
  - **Body**: 
    ```json
    {
      "name": "new name",
      "lastName": "new lastName",
      "enabled": true,
      "roleIdList" :[1]
    }
    ```
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "id": 1,
        "email": "example@example.com",
        "name": "new name",
        "lastName": "new lastName",
        "enabled": true,
        "role": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
            }
        ],
        "token": "verified",
        "createdAccount": "2024-08-09T09:18:08"
      }
      ```
- **Delete user**

  - **URL**: `/users/{id}`
  - **Method**: `Delete`
  - **Description**: Delete the user with the current ID.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "id": 1,
        "email": "example@example.com",
        "name": "name",
        "lastName": "lastName",
        "enabled": true,
        "role": [
            {
                "id": 1,
                "name": "ROLE_USER",
                "authority": "ROLE_USER"
            }
        ],
        "token": "verified",
        "createdAccount": "2024-08-09T09:18:08"
      }
      ```    
#### Donation Endpoints

- **Get all Donations**

  - **URL**: `/donations`
  - **Method**: `GET`
  - **Description**: Retrieve a list of all donations.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
        {
          "donationList": [
            {
            "id": 1,
            "quantity": 5,
            "category": [
                {
                    "id": 1,
                    "name": "ubrania, które nadają się do ponownego użycia"
                },
                {
                    "id": 5,
                    "name": "inne"
                }
            ],
            "institution": {
                "id": 3,
                "name": "Dla dzieci",
                "description": "Pomoc osobom znajdującym się w trudnej sytuacji życiowej."
            },
            "street": "zwirki",
            "city": "Warszawa",
            "zipCode": "62-500",
            "phoneNumber": 432554332,
            "pickUpDate": "2024-07-24",
            "pickUpTime": "16:22:00",
            "pickUpComment": "test",
            "receive": false,
            "createdDate": "2024-08-09",
            "createdTime": "10:31:00",
            "user": 2
        }
      
      ```
- **Get donation**

  - **URL**: `/donations/{id}`
  - **Method**: `GET`
  - **Description**: Retrieve details of a specific donation.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
        {
          "donationList": [
            {
            "id": 1,
            "quantity": 5,
            "category": [
                {
                    "id": 1,
                    "name": "ubrania, które nadają się do ponownego użycia"
                },
                {
                    "id": 5,
                    "name": "inne"
                }
            ],
            "institution": {
                "id": 3,
                "name": "Dla dzieci",
                "description": "Pomoc osobom znajdującym się w trudnej sytuacji życiowej."
            },
            "street": "zwirki",
            "city": "Warszawa",
            "zipCode": "62-500",
            "phoneNumber": 432554332,
            "pickUpDate": "2024-07-24",
            "pickUpTime": "16:22:00",
            "pickUpComment": "test",
            "receive": false,
            "createdDate": "2024-08-09",
            "createdTime": "10:31:00",
            "user": 2
        }
      ```   

- **Create Donation**

  - **URL**: `/donations/add`
  - **Method**: `POST`
  - **Description**: Create a new donation entry.
  - **Body**: 
    ```json
    {
        "quantity": 5,
        "categoryIdList": [1,5],
        "institutionId": 3,
        "street": "zwirki",
        "city": "Warszawa",
        "zipCode": "62-500",
        "phoneNumber": 432554332,
        "pickUpDate": "2024-07-24",
        "pickUpTime": "16:22:00",   
        "pickUpComment": "test",
        "userId": 2
    }
    ```
  - **Success Response**:
    - **Code**: `201 Created`
    - **Content**:
      ```json
      {
        "donationAddRequest": {
        "quantity": 5,
        "categoryIdList": [
            1,
            5
        ],
        "institutionId": 3,
        "street": "zwirki",
        "city": "Warszawa",
        "zipCode": "62-500",
        "phoneNumber": 432554332,
        "pickUpDate": "2024-07-24",
        "pickUpTime": "16:22:00",
        "receive": false,
        "createdDate": null,
        "createdTime": null,
        "pickUpComment": "test",
        "userId": 2
         }
      }
      ```


- **Update Donation**

  - **URL**: `/donations/{id}`
  - **Method**: `PUT`
  - **Description**: Update an existing donation.
  - **Body**: 
    ```json
    {
    "quantity": 333,
     "categoryIdList": [
        1,
        3
    ],
    "institutionId": 3,
    "donationAddress": {
        "street": "galeria",
        "city": "RAdom",
        "zipCode": "62-500",
        "phoneNumber": 432554332,
        "pickUpDate": "2024-07-30",
        "pickUpTime": "16:35:00",   
        "pickUpComment": "test"
        }, 
    "receive": true,
    "userId":  2
    }
    ```
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
      {
        "donation": {
        "id": 1,
        "quantity": 333,
        "category": [
            {
                "id": 1,
                "name": "ubrania, które nadają się do ponownego użycia"
            },
            {
                "id": 3,
                "name": "zabawki"
            }
        ],
        "institution": {
            "id": 3,
            "name": "Dla dzieci",
            "description": "Pomoc osobom znajdującym się w trudnej sytuacji życiowej."
        },
        "street": "galeria",
        "city": "RAdom",
        "zipCode": "galeria",
        "phoneNumber": 432554332,
        "pickUpDate": "2024-07-30",
        "pickUpTime": "16:35:00",
        "pickUpComment": "test",
        "receive": true,
        "createdDate": "2024-08-09",
        "createdTime": "10:31:00",
        "user": 2
      }
- **Delete donations**

  - **URL**: `/donations/{id}`
  - **Method**: `GET`
  - **Description**: Delete the donation with the current ID.
  - **Success Response**:
    - **Code**: `200 OK`
    - **Content**:
      ```json
    
        {
        "donation": {
        "id": 1,
        "quantity": 333,
        "category": [
            {
                "id": 1,
                "name": "ubrania, które nadają się do ponownego użycia"
            },
            {
                "id": 3,
                "name": "zabawki"
            }
        ],
        "institution": {
            "id": 3,
            "name": "Dla dzieci",
            "description": "Pomoc osobom znajdującym się w trudnej sytuacji życiowej."
        },
        "street": "galeria",
        "city": "RAdom",
        "zipCode": "galeria",
        "phoneNumber": 432554332,
        "pickUpDate": "2024-07-30",
        "pickUpTime": "16:35:00",
        "pickUpComment": "test",
        "receive": true,
        "createdDate": "2024-08-09",
        "createdTime": "10:31:00",
        "user": 2
        }
      ```   
## Usage
- Testing Endpoints: Use tools like Postman or curl to test the API endpoints.
- Authentication: Use the JWT token obtained from the login endpoint for protected routes.