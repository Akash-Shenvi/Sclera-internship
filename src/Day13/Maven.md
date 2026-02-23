# Maven – What I Learned

## Understanding Maven Project Structure

I understood that Maven follows a standardized directory layout, which improves code organization and team collaboration.

- **pom.xml** → Main project configuration file
- **src/main/java** → Application source code
- **src/main/resources** → Configuration files (application.properties, YAML, etc.)
- **src/test/java** → Test classes

This structured layout helps maintain a clean, scalable, and maintainable project architecture.

---

## Maven Core Concepts

I learned that Maven is built around the following core components:

- **POM (Project Object Model)** → Defined inside `pom.xml`
- **Project Coordinates**
- **Build Lifecycle**
- **Repositories**
- **Dependencies**

### Project Coordinates

Every Maven project is uniquely identified using:

- `groupId`
- `artifactId`
- `version`

Example:

```xml
<groupId>com.example</groupId>
<artifactId>student-management</artifactId>
<version>1.0.0</version>
```

These three elements uniquely define a project artifact.

---

## Dependencies

Dependencies are external libraries required for building the application.

Example:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

I learned that Maven automatically downloads the required JAR files instead of manually adding them to the project.

---

## Transitive Dependencies

When I added `spring-boot-starter-web`, Maven automatically downloaded additional required libraries such as:

- Spring Core
- Spring MVC
- Embedded Tomcat
- Jackson

These are called **transitive dependencies**, meaning dependencies that are required by another dependency.

---

## SNAPSHOT vs RELEASE Versions

I learned the difference between version types:

- **RELEASE** → Stable production-ready version (e.g., `1.0.0`)
- **SNAPSHOT** → Development version (e.g., `1.0.0-SNAPSHOT`)

During development, SNAPSHOT versions are typically used.  
For production environments, RELEASE versions are preferred.

---

## Dependency Scopes

Maven provides different scopes to control where dependencies are available.

- **compile** → Default scope, available everywhere
- **test** → Only available during testing (e.g., JUnit)
- **runtime** → Required only at runtime
- **provided** → Provided by the server/container

Example:

```xml
<scope>test</scope>
```

This ensures that test libraries are not included in the final packaged JAR file.

---

## Repositories

Maven retrieves dependencies from repositories:

- **Local Repository** → Stored in `.m2` directory on the system
- **Central Repository** → Maven Central (public online repository)
- **Remote Repository** → Private or organizational repository

I understood that Maven first checks the local repository before downloading dependencies from the central repository.

---

## Build Lifecycle

I explored Maven’s build lifecycle phases:

- validate
- compile
- test
- package
- install
- deploy

Common command used:

```
mvn clean install
```

This command:
- Removes old build files
- Compiles the project
- Executes test cases
- Generates the JAR/WAR file
- Installs it into the local repository

---

## Plugins and Goals

Maven performs tasks using plugins.

Example:

```
mvn spring-boot:run
```

- **Plugin** → spring-boot
- **Goal** → run

Plugins are responsible for compilation, testing, packaging, and running applications.

---

# Conclusion

Through learning Maven, I understood how it simplifies project management, dependency handling, and build automation while maintaining a clean and scalable project structure.