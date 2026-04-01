# Testing Setup and Implementation

- Added `spring-boot-starter-test` with JUnit 5, Mockito, AssertJ, and Spring Test.
- Added `spring-security-test` for authenticated controller testing and `h2` in-memory database for repository tests.
- Configured `maven-surefire-plugin` with `net.bytebuddy.experimental=true` for Java 17 compatibility.

## Service Layer Tests
- Used Mockito with `@ExtendWith(MockitoExtension.class)`, `@Mock`, and `@InjectMocks`.
- Created reusable test data in `@BeforeEach`.
- Tested post creation, slug generation, published post retrieval, authorization checks, and soft delete logic.

## Controller Layer Tests
- Used `@WebMvcTest` and `MockMvc` to test only the web layer.
- Mocked service dependencies and verified validation, status codes, secured endpoints, and `ApiResponse<T>` JSON structure.
- Covered success and failure cases for auth, post, and comment controllers.

## Exception Handling
- Verified `ResourceNotFoundException`, `BadRequestException`, and `UnauthorizedException`.
- Confirmed all errors return through `ApiResponse.error()` with correct HTTP status codes.