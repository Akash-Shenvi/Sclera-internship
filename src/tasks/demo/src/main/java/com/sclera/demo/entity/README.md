# Entity Design Specification
## Book and Author Domain Model

---

## 1. Purpose

This document defines the **entity structure and relational mapping** for the *Book–Author* domain in a Spring Boot application using **JPA (Jakarta Persistence API)**.  
The design follows industry best practices for database normalization, ORM mapping, and REST-safe serialization.

---

## 2. Domain Overview

The domain consists of two core entities:

- **Book**
- **Author**

Each book may be associated with multiple authors, and an author may be associated with multiple books.  
To ensure serialization safety and avoid cyclic dependencies, the relationship is implemented as a **unidirectional Many-to-Many association**.

---

## 3. Relationship Design

- **Relationship Type:** Many-to-Many
- **Direction:** Unidirectional
- **Owning Entity:** Book
- **Join Table:** `book_authors`

This design prevents circular references during JSON serialization and eliminates stack overflow risks commonly associated with bidirectional mappings.

---

## 4. Database Schema

### 4.1 `authors` Table

| Column Name | Type | Description |
|------------|------|------------|
| id | BIGINT | Primary key |
| first_name | VARCHAR | Author first name |
| last_name | VARCHAR | Author last name |
| country | VARCHAR | Author country |

---

### 4.2 `books` Table

| Column Name | Type | Description |
|------------|------|------------|
| id | BIGINT | Primary key |
| isbn_number | VARCHAR | Unique ISBN identifier |
| name | VARCHAR | Book title |
| category | VARCHAR | Book category |
| rating | DOUBLE | Book rating |
| price | DOUBLE | Book price |

---

### 4.3 `book_authors` Join Table

| Column Name | Type | Description |
|------------|------|------------|
| book_id | BIGINT | Foreign key referencing `books.id` |
| author_id | BIGINT | Foreign key referencing `authors.id` |

---

## 5. Entity: Author

### Description
Represents an author within the system.

### Characteristics
- Standalone entity with no reference to books
- Designed to remain lightweight and serialization-safe
- Suitable for reuse across multiple book associations

### Attributes
- `id` – Unique identifier
- `firstName` – Author’s first name
- `lastName` – Author’s last name
- `country` – Country of origin

---

## 6. Entity: Book

### Description
Represents a book within the system.

### Characteristics
- Owns the association with authors
- Maintains a collection of associated authors
- Uses a set-based association to avoid duplicates

### Attributes
- `id` – Unique identifier
- `isbnNumber` – ISBN value (unique)
- `name` – Book title
- `category` – Book category
- `rating` – Book rating
- `price` – Book price
- `authors` – Associated authors

---

## 7. Design Considerations

- Unidirectional mapping avoids cyclic data dependency
- Explicit join table ensures clarity and database portability
- No derived or calculated fields are persisted
- Entity structure is compatible with RESTful APIs and JSON serialization

---
