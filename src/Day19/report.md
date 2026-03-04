## Docker Deployment

After implementing all API features, the application was containerized using Docker to make deployment easier and to ensure the system runs consistently across different environments.

Docker enables packaging the application together with its runtime and required dependencies into containers. This removes the need to manually configure the environment on different machines.

### Building the Application

Before creating the container, the Spring Boot project was packaged into a JAR file using Maven.

```bash
mvn clean package
```
## Link
https://github.com/Akash-Shenvi/Sclera-internship/tree/main/src/tasks/demo