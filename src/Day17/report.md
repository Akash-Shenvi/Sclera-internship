# 📘 Library Management System – Development Report

Today, I worked on the Library Management System project using Spring Boot, Spring Data JPA, and MySQL. The main objective was to design and implement complete CRUD (Create, Read, Update, Delete) functionality for the core entities: Author and Book.

The project follows a layered architecture including Controller Layer (handles REST API requests), Service Layer (contains business logic), Repository Layer (handles database communication using JPA), and Entity Layer (defines database models). This structure helped me understand how professional Spring Boot applications are organized and maintained.

I created two main entities:

Author Entity:
- id
- firstName
- lastName
- country

Book Entity:
- id
- isbnNumber
- name
- category
- rating
- price

A unidirectional Many-to-Many relationship was implemented between Book and Author using JPA annotations with a join table (book_author), where Book owns the relationship.

CRUD operations implemented:

For Author:
- Create Author
- Get Author by ID
- Get All Authors
- Update Author
- Delete Author

For Book:
- Create Book
- Get Book by ID
- Get All Books
- Update Book
- Delete Book

All endpoints were exposed as REST APIs and tested successfully using Postman.

Database Integration:
- Configured MySQL connection in application.properties
- Used Spring Data JPA for automatic query generation
- Enabled automatic table updates using ddl-auto: update
- Verified table creation and tested insert and fetch operations successfully


This task helped strengthen my understanding of JPA relationships, layered architecture, REST API development, and real-time database integration using Spring Boot.

## Link
https://github.com/Akash-Shenvi/Sclera-internship/tree/main/src/tasks/library