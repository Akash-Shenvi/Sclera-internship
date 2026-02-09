# 📘 Book–Author Management REST API
**Spring Boot · JPA · RESTful Design**

---

## 1. Overview

This document describes the REST API endpoints for managing Books and Authors in a Spring Boot application.

The system supports:
* **CRUD operations** for Books and Authors
* **Many-to-Many relationship** using a join table
* **Aggregation endpoints** using JPA (`AVG`, `GROUP BY`)
* **Pagination and sorting** for large datasets

The API follows REST best practices and clean layered architecture.

---

## 2. Base URL
`/api`

---

## 3. Domain Summary

### Entities

| Entity | Attributes |
| :--- | :--- |
| **Book** | `id`, `isbnNumber`, `name`, `category`, `rating`, `price` |
| **Author** | `id`, `firstName`, `lastName`, `country` |

### Relationship
* **Type:** Many-to-Many (Unidirectional)
* **Join Table:** `book_author`
* **Ownership:** `Book` owns the relationship

---

## 4. Book APIs

### 4.1 Create Book
Creates a new book and associates it with one or more authors.

* **Endpoint:** `POST /api/books`
* **Request Body:**
    ```json
    {
      "isbnNumber": "9780134685991",
      "name": "Effective Java",
      "category": "Programming",
      "rating": 4.8,
      "price": 650,
      "authorIds": [1, 2]
    }
    ```

### 4.2 Get Book by ID
Fetches a single book by its unique identifier.

* **Endpoint:** `GET /api/books/{id}`

### 4.3 Get All Books
Returns a list of all books without pagination.

* **Endpoint:** `GET /api/books`

### 4.4 Update Book
Updates an existing book’s details and author associations.

* **Endpoint:** `PUT /api/books/{id}`

### 4.5 Delete Book
Deletes a book by ID.

* **Endpoint:** `DELETE /api/books/{id}`

### 4.6 Get Books with Pagination & Sorting
Returns paginated and sorted book data using Spring Data JPA pagination.

* **Endpoint:** `GET /api/books/page`
* **Query Parameters:**
    * `page`: Page number (0-based)
    * `size`: Number of records per page
    * `sort`: `field,direction` (asc | desc)
* **Example:**
    ```
    /api/books/page?page=0&size=5&sort=price,desc
    ```

---

## 5. Author APIs

### 5.1 Create Author
Creates a new author record.

* **Endpoint:** `POST /api/authors`
* **Request Body:**
    ```json
    {
      "firstName": "Joshua",
      "lastName": "Bloch",
      "country": "USA"
    }
    ```

### 5.2 Get Author by ID
* **Endpoint:** `GET /api/authors/{id}`

### 5.3 Get All Authors
* **Endpoint:** `GET /api/authors`

### 5.4 Update Author
* **Endpoint:** `PUT /api/authors/{id}`

### 5.5 Delete Author
* **Endpoint:** `DELETE /api/authors/{id}`

---

## 6. Aggregation & Analytics APIs

### 6.1 Get Overall Average Rating
Returns the average rating of all books in the system. The value is calculated dynamically using JPA aggregation.

* **Endpoint:** `GET /api/authors/average-rating`
* **Response:**
    ```json
    {
      "averageRating": 4.3
    }
    ```

### 6.2 Get Authors with Average Book Price
Returns all authors along with the average price of their books. The average price is not persisted and is returned using a DTO.

* **Endpoint:** `GET /api/authors/avg-book-price`
* **Query Parameter:**
    * `sort`: `asc` | `desc`
* **Example:** `/api/authors/avg-book-price?sort=asc`
* **Response:**
    ```json
    [
      {
        "authorId": 1,
        "fullName": "Joshua Bloch",
        "country": "USA",
        "avgBookPrice": 720.50
      }
    ]
    ```

---

## 7. Design Considerations

* **Mapping:** Unidirectional Many-to-Many mapping avoids cyclic serialization issues.
* **Aggregation:** Values are computed efficiently using JPQL (`AVG`, `GROUP BY`) rather than in-memory Java processing.
* **DTO Pattern:** DTOs are used for non-persistent and response-specific data to decouple entities from the API layer.
* **Pagination:** Leverages Spring Data JPA standards (`Pageable`).
* **Architecture:** Controllers are kept thin; all business logic resides in services.

---


