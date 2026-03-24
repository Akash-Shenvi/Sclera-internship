# Authentication & Authorization

## User Management
- Register a new user with the following fields:
    - Full Name
    - Email Address
    - Password
    - Profile Description (Bio)

- Authenticate users using email and password
- Generate a JWT token upon successful login

## Security Configuration
- Implement JWT-based authentication
- Token validity: 24 hours
- Apply role-based access control:
    - USER
    - ADMIN

## User Operations
- Fetch details of the currently authenticated user


---

# Blog Management System

## Post Operations
- Create a blog post with:
    - Title
    - Content
    - Cover Image
    - Publish Status (Draft/Published)

- Update an existing post:
    - Only allowed for the post creator

- Soft delete a post:
    - Mark as deleted instead of permanent removal
    - Only the owner can perform this action

## Retrieval Features
- Fetch all posts:
    - Supports pagination
    - Sorting by fields (e.g., date, views)

- Retrieve a single post:
    - Automatically increments view count on access

- Fetch posts created by a specific user

## Analytics
- Maintain and track post view counts


---

# Standard API Response Format

All API responses should follow a consistent structure:

- `success` → Indicates request status (true/false)
- `message` → Human-readable response message
- `data` → Payload containing requested information
- `errors` → Validation or processing errors (if any)