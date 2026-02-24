

# Core Spring Boot Fundamentals

### Dependency Injection (DI)

I understood that Dependency Injection is a core principle in Spring that promotes loose coupling between components. Instead of instantiating dependencies manually using `new`, the Spring container injects required objects automatically. I learned that constructor-based injection is recommended over field injection because it improves immutability, testability, and clarity of required dependencies.

---

### Spring Beans and Bean Lifecycle

I learned that Spring Beans are objects created and managed by the IoC (Inversion of Control) container. The container controls their complete lifecycle, which includes:

1. Bean instantiation
2. Dependency injection
3. Initialization
4. Destruction

Understanding this lifecycle helped me see how Spring manages application components internally and ensures proper object management.

---

### Key Spring Boot Annotations

I gained clarity on the purpose and usage of common annotations:

- **@SpringBootApplication** – Entry point annotation that enables auto-configuration, component scanning, and configuration support.
- **@Component** – Marks a class as a generic Spring-managed bean.
- **@Service** – Indicates the service layer containing business logic.
- **@Repository** – Represents the data access layer and enables exception translation.
- **@Autowired** – Performs automatic dependency injection.

These annotations helped me clearly understand layered architecture and separation of concerns.

---

### Application Configuration (Properties & YAML)

I learned how to manage configuration using:

- `application.properties`
- `application.yml`

I configured:

- MySQL database connection
- Server port settings
- Hibernate properties

This strengthened my understanding of environment-specific configuration and externalized settings.

---

### Auto-Configuration in Spring Boot

I understood that Spring Boot automatically configures required beans based on dependencies present in the `pom.xml`.

For example, when adding Spring Data JPA dependency, Spring Boot automatically configures:

- DataSource
- Hibernate
- Transaction management

This significantly reduces manual setup and speeds up development.

---

# Building REST APIs – Learning

## REST Architectural Principles

I learned core REST concepts, including:

- Stateless communication
- Resource-oriented URLs
- Standard HTTP methods (GET, POST, PUT, DELETE)
- JSON as the data exchange format

This provided a strong foundation for designing scalable web APIs.

---

## Creating REST Controllers

I learned that `@RestController` simplifies API development by combining `@Controller` and `@ResponseBody`, allowing direct JSON responses. Controllers handle client requests and return appropriate responses.

---

## Request Mapping Annotations

I practiced mapping HTTP methods to controller methods using:

- `@GetMapping`
- `@PostMapping`
- `@PutMapping`
- `@DeleteMapping`
- `@RequestMapping`

This helped me understand how endpoints are structured and managed.

---

## Path Variables vs Request Parameters

I learned the distinction between:

- `@PathVariable` – Used for dynamic values in URL paths
- `@RequestParam` – Used for query parameters

This improved my ability to design flexible and clean APIs.

---

## Request Body and Response Handling

I understood:

- `@RequestBody` converts incoming JSON into Java objects.
- `ResponseEntity` allows control over HTTP status codes and response structure.

This enhanced my understanding of proper API response design.

---

## Global Exception Handling

I implemented centralized exception handling using:

- `@ControllerAdvice`
- `@ExceptionHandler`

This improved application robustness and ensured consistent error responses across APIs.

---

## API Testing with Postman

I tested REST endpoints using Postman by sending various HTTP requests and validating:

- Response body
- HTTP status codes
- Error handling

This improved my debugging and API validation skills.

---

# Database Integration – Learning

## JDBC Fundamentals

I first learned how JDBC enables Java applications to connect to relational databases. JDBC requires manual:

- Connection handling
- SQL writing
- Resource management

This helped me appreciate higher-level abstractions provided by Spring.

---

## Spring Data JPA

I understood that Spring Data JPA simplifies database operations by providing built-in CRUD methods through `JpaRepository`. This eliminates boilerplate code and improves development efficiency.

---

## Hibernate ORM

I learned that Hibernate is an ORM (Object-Relational Mapping) framework that:

- Maps Java classes to database tables
- Automatically generates SQL queries
- Manages entity state transitions

This reduces the need for manual SQL in most scenarios.

---

## Entity and Repository Pattern

I understood that:

- **Entities** represent database tables.
- **Repositories** handle data access logic.

This reinforced the concept of separation of concerns in backend architecture.

---

## CRUD Operations and DTO Usage

I practiced implementing Create, Read, Update, and Delete operations using `JpaRepository`.

I also learned the importance of DTOs (Data Transfer Objects) to:

- Prevent exposing internal entity structures
- Improve security
- Control API response structure

---

## Entity Relationships

I gained knowledge of relational mappings:

- One-to-One
- One-to-Many
- Many-to-Many

I learned how annotations like `@OneToMany`, `@ManyToOne`, and `@ManyToMany` define relationships between entities and manage foreign key associations.

---

## Pagination and Sorting

I implemented pagination using:

- `Pageable`
- `PageRequest`

This allowed efficient handling of large datasets and improved API performance through controlled data retrieval.

---

# Overall Outcome

Through this learning journey, I developed a structured understanding of:

- Spring Boot architecture
- RESTful API development
- Database integration using JPA and Hibernate
- Clean layered backend design

This foundation enables me to build scalable, maintainable, and production-ready backend applications.