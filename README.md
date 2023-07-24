RecipeHub - Spring Boot 3.1 Project

1. Description

- RecipeHub is a demo project for Spring Boot, designed to provide a platform for managing recipes and cooking instructions. This project utilizes various Spring Boot starters and dependencies to implement essential features, including data persistence, security, web interfaces, and email functionalities.

2. Prerequisites

* Before running the RecipeHub application, ensure you have the following installed and set up on your system:
- Java Development Kit (JDK) 17 or later.
- MySQL database server.
- Maven build tool.
- Integrated Development Environment (IDE) of your choice (e.g., IntelliJ IDEA, Eclipse).

3. Dependencies

* The RecipeHub project relies on the following key dependencies:
- Spring Boot Starter Data JPA: Provides support for data access using Spring Data JPA.
- Spring Boot Starter Security: Enables security features and authentication mechanisms.
- Spring Boot Starter Web: Facilitates web application development with Spring MVC.
- Spring Boot Starter Validation: Adds support for data validation in the application.
- Spring Boot Starter WebFlux: Offers reactive web capabilities using Spring WebFlux.
- Spring Boot Starter Thymeleaf: Integrates Thymeleaf as a templating engine for HTML views.
- Spring Boot Starter Mail: Enables sending email notifications.
- Spring Boot DevTools: Provides development tools for faster application restarts.
- MySQL Connector/J: MySQL database driver for Spring Boot's data access.
- Spring Boot Starter Test: Includes dependencies for unit and integration testing.
- Spring Security Test: Adds testing support for Spring Security features.
- JWT (JSON Web Token): Supports JSON Web Token for user authentication.
- MapStruct: Offers mapping capabilities for data transfer objects.
- Lombok: Provides annotations to reduce boilerplate code.
- Apache POI: Used for working with Microsoft Office documents, such as Excel.
- OpenCSV: Facilitates reading and writing CSV files.
- SpringDoc OpenAPI Starter WebMVC UI: Integrates OpenAPI documentation using SpringDoc.

4. Features
- Create, read, update, and delete recipes.
- User authentication and access control using JSON Web Tokens (JWT).
- Web interfaces for users to manage their recipes.
- Email notifications for account-related activities.
- Export recipes to Excel and import recipes from CSV files.
- API documentation through OpenAPI specification.

5. Documentation
- Detailed API documentation and usage instructions are available through the Swagger UI. Access the documentation by running the application and navigating to http://localhost:8080/swagger-ui.html.
