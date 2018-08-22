# RestFul Standalone application that processes Trade Validation.

- The application is based on Spring and most of its libraries by using Microservices Architecture concepts in order to create a lightweight enterprise service, highly scalable and available that runs independently from an external container but still provides enterprise ready features.

- The concern about code coverage is very relevant in that application. In order to guarantee 100% percent of code coverage, Jacoco has been configured during maven build. If the application doesn't achieve 100% of test coverage, the build will fail. After successful build, go on /target/site/index.html to see more details about project code coverage (Classes that are not related to business were excluded from Jacoco's validation).

- In order to guarantee documentation of rest services, Swagger has been configured so that it gives us some documentation about the rest services and its methods, schemas, parameters, URL etc. After starting the application go on http://localhost:8080/trade/swagger-ui.html. You can also get response from the application through this UI (using differents methods, parameters, url, etc.)

- This systems utilizes H2 Database in-memory to attend some of the requirements.

# Used libraries
- JDK-8
- Maven
- Spring Boot
- Swagger (rest documentation)

# Persistence
- H2 data persistence 
- Spring Data

# Testing
- Junit
- Mockito - Library for creating mock objects/behaviour for unit tests
- Mock-mvc-test - to test rest service from URL.
- Jacoco – Test code coverage.

# Building

- From terminal: (Java 8 and Maven 3 are required)
Go on the project's root folder, then type: mvn clean install

# Running

- From terminal:
Go on the project's root folder, then type: mvn spring-boot:run