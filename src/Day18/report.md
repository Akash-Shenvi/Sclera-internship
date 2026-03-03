# 📘 Work Report – Pagination, Sorting & Exception Handling
**Spring Boot · Spring Data JPA · REST API**

---

## Overview

Today, I implemented **pagination, sorting (ascending & descending), and global exception handling** in the Book–Author Management REST API to improve performance, scalability, and error management.

---

## 1️⃣ Pagination

### Endpoint
GET /api/books/page

### Query Parameters
- `page` → Page number (0-based)
- `size` → Number of records per page

### Example
GET /api/books/page?page=0&size=5

### Implementation
- Used `Pageable` interface
- Implemented `PageRequest.of(page, size)`
- Returned `Page<Book>` response

### Benefit
- Loads only required records
- Improves response time
- Efficient handling of large datasets

---

## 2️⃣ Sorting (ASC / DESC)

### Supported
- Ascending (`asc`)
- Descending (`desc`)
- Dynamic field-based sorting

### Example
GET /api/books/page?page=0&size=5&sort=price,desc

### Implementation
- Used `Sort.by(direction, field)`
- Integrated sorting with `PageRequest`
- Supports fields like `price`, `rating`, `name`

### Benefit
- Flexible client-side sorting
- Clean REST query parameter design
- Optimized database-level sorting

---

## 3️⃣ Global Exception Handling

### Implemented Using
- `@RestControllerAdvice`
- `@ExceptionHandler`
- Custom exception class (`ResourceNotFoundException`)

### Handled Errors
- 404 – Resource Not Found
- 400 – Bad Request
- 500 – Internal Server Error
## Link
https://github.com/Akash-Shenvi/Sclera-internship/tree/main/src/tasks/demo

### Sample Error Response
```json
{
  "timestamp": "2026-03-03T21:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Book not found with id: 10",
  "path": "/api/books/10"
}


