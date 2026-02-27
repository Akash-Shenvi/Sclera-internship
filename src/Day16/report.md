# Docker Deployment & Spring Security – Notes

## Dockerizing a Spring Boot Application

Docker allows applications to run consistently across different environments by packaging the application along with its dependencies into a container.

---

## Step 1: Build the JAR File

Generate the executable JAR using Maven:

```bash
mvn clean package
```

After a successful build, the JAR file is created inside:

```
target/demo-0.0.1-SNAPSHOT.jar
```

This JAR contains the compiled application and embedded server.

---

## Step 2: Create Dockerfile

Create a file named `Dockerfile` in the project root directory.

```dockerfile
FROM openjdk:17
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Explanation

- `FROM openjdk:17` → Uses Java 17 as the base runtime image
- `COPY` → Copies the built JAR into the container
- `ENTRYPOINT` → Runs the Spring Boot application

---

## Step 3: Build Docker Image

```bash
docker build -t springboot-app .
```

This creates a Docker image named `springboot-app`.

---

## Step 4: Run Docker Container

```bash
docker run -p 8080:8080 springboot-app
```

This maps container port 8080 to local port 8080.

The Spring Boot application now runs inside Docker.

---

# Spring Security Basics

Adding Spring Security automatically protects all endpoints.

## Dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

After adding this dependency, authentication is required for all endpoints.

---

## Authentication vs Authorization

- **Authentication** → Confirms user identity (Who are you?)
- **Authorization** → Determines access permissions (What can you access?)

---

## HTTP Basic Authentication Configuration

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }
}
```

### What This Does

- Secures all endpoints
- Allows only `ADMIN` role to access `/admin/**`
- Enables HTTP Basic authentication

---

# JWT Authentication (Stateless Security)

JWT (JSON Web Token) is used for stateless authentication in REST APIs.

## Flow

1. User logs in with credentials
2. Server validates credentials
3. Server generates a JWT token
4. Client stores the token
5. Client sends token in header with each request
6. Server validates token

### Example Header

```
Authorization: Bearer <JWT_TOKEN>
```

JWT removes the need for server-side session storage.

---

# Role-Based Access Control (RBAC)

Access control based on roles:

```java
.authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin/**").hasRole("ADMIN")
    .requestMatchers("/user/**").hasRole("USER")
)
```

### Access Rules

- `/admin/**` → Only ADMIN
- `/user/**` → Only USER

---

# Key Points

- Docker ensures environment consistency
- Spring Security secures endpoints automatically
- Authentication verifies identity
- Authorization controls access
- HTTP Basic is simple authentication
- JWT provides scalable stateless authentication
- Role-based rules enforce structured access control  