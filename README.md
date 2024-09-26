
# 📚 Rent Read Application

## Overview
Rent Read is a `Spring Boot-based` web application designed for users to rent and return books online easily. The application features user `authentication, role-based access control, and a RESTful API` for managing books. This project allows users to interact with a library system from anywhere.

![Library Image](https://cdn.pixabay.com/photo/2020/05/23/20/08/books-5211309_1280.jpg)

## Tech Stack

- **Java**: Core programming language used for building the application.
- **Spring Boot**: Framework for simplifying Java development and creating the RESTful APIs.
- **Spring Security**: For managing authentication and role-based access control.
- **Gradle**: Build automation tool to manage project dependencies and build tasks.
- **MySQL**: Relational database management system for data persistence.
- **Hibernate**: ORM (Object-Relational Mapping) framework to interact with the database using JPA.
- **BCrypt**: For encrypting user passwords to enhance security.
- **ModelMapper**: For object mapping between DTOs and entities.
- **SLF4J**: Logging framework for tracing application activities and errors.
- **Postman**: Tool for testing the API endpoints.

## Setup

To set up and run the RentRead API locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/Jyotipr45/RentRead_v1.git
   ```
2. Navigate to the project directory:
   ```bash
   cd RentRead
   ```
3. Build the project using Gradle:
   ```bash
   gradle clean build
   ```
4. Run
   ```java
   ./gradlew bootRun
   ```


## API Endpoints and Interactions

### User Management

#### Register User
- **Endpoint:** `POST /user/register`
- **Description:** Registers a new user in the system.
- **Request Body:**
  ```json
  {
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123",
    "role": "USER"
  }
  ```
- **Response:**
  - Status: 201 Created
  - Body:
    ```json
    {
      "id": 1,
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "password": "encrypted-password",
      "role": "USER",
      "rentedBooks": []
    }
    ```

#### User Login
- **Endpoint:** `POST /user/login`
- **Request Body:**
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response:**
  - Status : 200 OK ✅
  - Body : ```Login successful```

### Book Management

#### Create Book
- **Endpoint:** `POST /books`
- **Description:** Adds a new book to the system.
- **Request Body:**
  ```json
  {
    "title": "Sample Book",
    "author": "Jane Smith",
    "genre": "Thriller",
    "availabilityStatus": "AVAILABLE"
  }
  ```
- **Response:**
  - Status: 201 Created 
  - Body:
    ```json
    {
      "id": 1,
      "title": "Sample Book",
      "author": "Jane Smith",
      "genre": "Thriller",
      "availabilityStatus": "AVAILABLE"
    }
    ```

#### Get All Books
- **Endpoint:** `GET /books`
- **Description:** Retrieves all books in the system.
- **Response:**
  - Status: 200 OK ✅
  - Body:
    ```json
    [
      {
        "id": 1,
        "title": "Sample Book",
        "author": "Jane Smith",
        "genre": "Thriller",
        "availabilityStatus": "AVAILABLE"
      },
      {
        "id": 2,
        "title": "Another Book",
        "author": "John Doe",
        "genre": "Mystery",
        "availabilityStatus": "AVAILABLE"
      }
    ]
    ```

#### Update Book
- **Endpoint:** `PUT /books/{id}`
- **Description:** Updates information about a specific book.
- **Request Body:**
  ```json
  {
    "title": "Updated Book Title",
    "author": "Jane Smith",
    "genre": "Thriller",
    "availabilityStatus": "AVAILABLE"
  }
  ```
- **Response:**
  - Status: 200 OK ✅
  - Body:
    ```json
    {
      "id": 1,
      "title": "Updated Book Title",
      "author": "Jane Smith",
      "genre": "Thriller",
      "availabilityStatus": "AVAILABLE"
    }
    ```

#### Delete Book
- **Endpoint:** `DELETE /books/{bookId}`
- **Description:** Deletes a book by it's `bookId` from the system.
- **Response:**
  - Body : Successfully deleted book with ID: `{bookId}`

### Rental Management

#### Rent Book
- **Endpoint:** `POST /books/{bookId}/rent`
- **Description:** Allows a user to rent a book.
- **Response:**
  - Status: 200 OK ✅
  - Body:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "rentedBooks": [
        {
          "id": 1,
          "title": "Updated Book Title",
          "author": "Jane Smith",
          "genre": "Thriller",
          "availabilityStatus": "NOT AVAILABLE"
        }
      ],
      "role": "USER"
    }
    ```

#### Return Book
- **Endpoint:** `POST /books/{bookId}/return`
- **Description:** Allows a user to return a rented book.

- **Response:**
  - Status: 200 OK ✅
  - Body:
    ```json
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john@example.com",
      "rentedBooks": [],
      "role": "USER"
    }
    ```

### Admin Operations

#### Add Book
- **Endpoint:** `POST /books`
- **Description:** Adds a new book to the system. (Admin Only)
- **Request Body:**
  ```json
  {
    "title": "New Book",
    "author": "Admin Author",
    "genre": "Fiction",
    "availabilityStatus": "AVAILABLE"
  }
  ```
- **Response:**
  - Status: 201 Created 🎉
  - Body:
    ```json
    {
      "id": 3,
      "title": "New Book",
      "author": "Admin Author",
      "genre": "Fiction",
      "availabilityStatus": "AVAILABLE"
    }
    ```

#### Update Book (Admin Only)
- **Endpoint:** `PUT /books/{bookId}`
- **Description:** Updates information about a specific `bookId`. (Admin Only)
- **Request Body:**
  ```json
  {
    "title": "Updated Book Title",
    "author": "Admin Author",
    "genre": "Fiction",
    "availabilityStatus": "AVAILABLE"
  }
  ```
- **Response:**
  - Status: 200 OK ✅
  - Body:
    ```json
    {
      "id": 1,
      "title": "Updated Book Title",
      "author": "Admin Author",
      "genre": "Fiction",
      "availabilityStatus": "AVAILABLE"
    }
    ```

#### Delete Book (Admin Only)
- **Endpoint:** `DELETE /books/{id}`
- **Description:** Deletes a book from the system. (Admin Only)
- **Response:**
  - Status: 200 OK ✅
  - Body : Successfully deleted book with ID: `{bookId}`

## Sample User

For testing purposes, you can use the following sample user:

- **First Name**: John
- **Last Name**: Doe
- **Email**: john@example.com,
- **Password**: password123
- **Role**: USER

## Logging

  - The application uses SLF4J for logging. Logs are generated for important operations such as user actions, API requests, and exceptions. This aids in debugging and tracking user activities within the application.📝

## Contributing
  Feel free to fork the repository and submit pull requests for any enhancements or bug fixes.

  GitHub Repository: [https://github.com/Jyotipr45/RentRead_v1](https://github.com/Jyotipr45/RentRead_v1)  

## License  

  This project is licensed under the [MIT License](https://opensource.org/licenses/MIT) - see the LICENSE file for details.


