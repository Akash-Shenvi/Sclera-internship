## JWT Authentication – Implementation

The application was secured using **JWT (JSON Web Token)** for authentication with **Spring Security**. Security components were implemented to handle token validation and user authentication.

To begin with, **Spring Security** and **JWT dependencies** were added to the `pom.xml` file.

A **JWT utility class** was then created to handle token generation and validation using a **secret key**. This class generates a JWT token during user login and extracts the username from the token for validating incoming requests.

Overall, JWT helps ensure that only authenticated users can access protected API endpoints by verifying the token included in each request.

---

## JWT Authentication – Additional Components

A class named **CustomUserDetailsService** was implemented to fetch user information from the database. In this project, users are the entities that need authentication, so the service retrieves user details based on the **username** stored in the database.

This class integrates with **Spring Security** and loads user-specific data required during the authentication process.

Another component called **JWT Filter** was implemented to intercept all incoming HTTP requests. This filter checks whether a **JWT token** is present in the request header.

If a token is found, the filter validates it using the JWT utility class. After successful verification, the authenticated user details are set in the **Spring Security context**, allowing the request to proceed to protected API endpoints.

---

## Spring Security Configuration and Authentication Controller

A **Security Configuration** class was implemented to configure the **Spring Security settings** for the application. In this configuration, **CSRF protection is disabled** because the application exposes **REST API endpoints** that use **stateless authentication with JWT**.

The configuration also defines access rules for the endpoints:
- The **login endpoint** is accessible **without authentication**.
- All other API endpoints are **protected** and require a **valid JWT token**.

Finally, an **Authentication Controller** was implemented to handle **user login requests**. When a user submits valid credentials, the system authenticates the user and generates a **JWT token**.

The generated token is then returned in the **response**, and the client must include this token in the **Authorization header** for all subsequent API requests to access protected resources.

---

## MapStruct

I learned about **MapStruct**, a Java library used for **automatic object mapping** between different layers of an application.

In a **Spring Boot application**, it is considered a best practice **not to expose database entities directly through APIs**. Instead, **Data Transfer Objects (DTOs)** are used to send only the required data to the client.

MapStruct helps simplify this process by automatically generating mapping code between **entities and DTOs**, reducing boilerplate code and improving maintainability.

### MapStruct Dependency

```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.5.Final</version>
</dependency>

---
### How MapStruct Works

MapStruct helps eliminate the need to write repetitive mapping code by **automatically generating the mapping implementation at compile time**.  
This is done by creating a **mapper interface** and annotating it with the `@Mapper` annotation.

During compilation, MapStruct generates the implementation class that performs the object mapping between entities and DTOs.

### Benefits of Using MapStruct

- **Improved Code Readability** – Keeps the code clean and easier to understand.
- **Reduced Boilerplate Code** – Eliminates the need for manually writing repetitive mapping logic.
- **Better Performance** – Mapping code is generated at **compile time**, so it does not rely on reflection and runs faster.
