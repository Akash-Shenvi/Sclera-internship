# Book-Author API Testing Guide

This README is focused on testing all implemented endpoints in Postman.

## Base URL

`http://localhost:8080`

## Prerequisites

1. Start MySQL and ensure database config in `src/main/resources/application.properties` is valid.
2. Run the app:
   - Windows: `.\mvnw.cmd spring-boot:run`
3. Open Postman and set an environment variable:
   - `baseUrl = http://localhost:8080`

## Author Endpoints

### 1. Create Author

- Method: `POST`
- URL: `{{baseUrl}}/api/authors`
- Body (JSON):

```json
{
  "firstName": "Joshua",
  "lastName": "Bloch",
  "country": "USA"
}
```

### 2. Get All Authors

- Method: `GET`
- URL: `{{baseUrl}}/api/authors`

### 3. Get Author By ID

- Method: `GET`
- URL: `{{baseUrl}}/api/authors/{id}`

### 4. Update Author

- Method: `PUT`
- URL: `{{baseUrl}}/api/authors/{id}`
- Body (JSON):

```json
{
  "firstName": "Updated",
  "lastName": "Author",
  "country": "India"
}
```

### 5. Delete Author

- Method: `DELETE`
- URL: `{{baseUrl}}/api/authors/{id}`

### 6. Average Rating of All Books

- Method: `GET`
- URL: `{{baseUrl}}/api/authors/average-rating`

### 7. Authors with Average Book Price (Sorted)

- Method: `GET`
- URL (ascending): `{{baseUrl}}/api/authors/avg-book-price?sort=asc`
- URL (descending): `{{baseUrl}}/api/authors/avg-book-price?sort=desc`

## Book Endpoints

### 1. Create Book

- Method: `POST`
- URL: `{{baseUrl}}/api/books`
- Body (JSON):

```json
{
  "isbnNumber": "9780134685991",
  "name": "Effective Java",
  "category": "Programming",
  "rating": 4.8,
  "price": 650.0,
  "authorIds": [1, 2]
}
```

### 2. Get All Books

- Method: `GET`
- URL: `{{baseUrl}}/api/books`

### 3. Get Book By ID

- Method: `GET`
- URL: `{{baseUrl}}/api/books/{id}`

### 4. Update Book

- Method: `PUT`
- URL: `{{baseUrl}}/api/books/{id}`
- Body (JSON):

```json
{
  "isbnNumber": "9780134685991",
  "name": "Effective Java 3rd",
  "category": "Programming",
  "rating": 4.9,
  "price": 700.0,
  "authorIds": [1]
}
```

### 5. Delete Book

- Method: `DELETE`
- URL: `{{baseUrl}}/api/books/{id}`

### 6. Paginated + Sorted Books

- Method: `GET`
- URL: `{{baseUrl}}/api/books/page?page=0&size=5&sort=price,desc`

Other examples:

- `{{baseUrl}}/api/books/page?page=0&size=10&sort=id,asc`
- `{{baseUrl}}/api/books/page?page=1&size=5&sort=name,asc`

## Exception Handling Test Cases

### 1. Not Found (Book)

- Request: `GET {{baseUrl}}/api/books/9999`
- Expected: `404 Not Found`

### 2. Not Found (Author)

- Request: `GET {{baseUrl}}/api/authors/9999`
- Expected: `404 Not Found`

### 3. Conflict on Author Delete

- Preconditions:
  - Create an author.
  - Create a book using that author in `authorIds`.
- Request: `DELETE {{baseUrl}}/api/authors/{assignedAuthorId}`
- Expected: `409 Conflict`
- Expected message includes: author is assigned to one or more books.

### 4. Bad Request for Invalid Sort Direction

- Request: `GET {{baseUrl}}/api/books/page?page=0&size=5&sort=price,wrong`
- Expected: `400 Bad Request`

## Typical Execution Order in Postman

1. Create 2 authors.
2. Create 1 or more books using those author IDs.
3. Test book and author CRUD.
4. Test aggregation endpoints.
5. Test paginated/sorted books endpoint.
6. Test exception scenarios.
