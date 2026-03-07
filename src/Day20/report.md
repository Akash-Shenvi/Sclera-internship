## JWT Authentication – Implementation

The application was secured using **JWT (JSON Web Token)** for authentication with **Spring Security**. Security components were implemented to handle token validation and user authentication.

To begin with, **Spring Security** and **JWT dependencies** were added to the `pom.xml` file.

A **JWT utility class** was then created to handle token generation and validation using a **secret key**. This class generates a JWT token during user login and extracts the username from the token for validating incoming requests.

Overall, JWT helps ensure that only authenticated users can access protected API endpoints by verifying the token included in each request.
