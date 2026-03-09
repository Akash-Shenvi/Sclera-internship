# Authentication Module Progress

**Date:** March 9, 2026  
**Repository:** https://github.com/Akash-Shenvi/Sclera-internship/tree/main/src/tasks/demo

## Overview
Today I worked on building the core foundation for JWT-based authentication in the Spring Boot application. The focus was on setting up user authentication, secure password handling, and token generation.

## Work Completed

### 1. User Entity Updates
- Added authentication fields to the `User` entity:
    - `email`
    - `password`
    - `role`
- Added proper constraints to maintain data integrity.

### 2. Repository Methods
Extended `UserRepository` with authentication-related methods:
- `findByEmail(String email)`
- `existsByEmail(String email)`

### 3. Password Security
- Configured `BCryptPasswordEncoder` for secure password hashing.
- Added `PasswordEncoder` bean in the security configuration.

### 4. Authentication DTOs
Created DTOs to handle authentication requests and responses:
- `RegisterRequest`
- `LoginRequest`
- `AuthResponse`

### 5. Register Flow Implementation
Implemented user registration logic in the service layer:
- Check if email already exists.
- Hash the password using `BCrypt`.
- Save the user in the database.

### 6. Login Flow Implementation
Implemented login logic:
- Fetch user using email.
- Verify password using `PasswordEncoder`.
- Generate JWT token after successful authentication.

### 7. JWT Service
Created `JwtService` responsible for:
- Generating JWT tokens
- Parsing token claims
- Validating tokens

### 8. JWT Improvements
- Changed JWT subject to use `userId` instead of email.
- Added `role` as a claim inside the token.

### 9. Application Configuration
Added JWT configuration in `application.properties`:
- `jwt.secret`
- `jwt.expiration`

### 10. Controller Layer
- Implemented authentication controller.
- Added **register endpoint**.
- Prepared **login endpoint** for authentication flow.

## Outcome
The base structure for **JWT authentication** is now implemented including:
- User registration
- Login verification
- Secure password storage
- JWT token generation

Next step will be integrating JWT filters and securing application endpoints with Spring Security.