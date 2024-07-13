# Shopping Cart Application

This is a Spring Boot application for managing a shopping cart, allowing users to add items to the cart, update item quantities, remove items, view cart totals, and checkout. The application uses Kotlin and PostgreSQL for persistence. The project is containerized using Docker and Docker Compose for easy deployment.

## Features

- Add items to the shopping cart
- Update item quantities in the cart
- Remove items from the cart
- View the price of items in the cart
- Use the same shopping cart until it's checked out
- View the cart total
- Checkout the cart

## Prerequisites

- Docker
- Docker Compose

## Project Structure

- `src/main/kotlin`: Contains the Kotlin source code for the application.
- `src/main/resources`: Contains the application properties and other resources.
- `build.gradle.kts`: Gradle build configuration file.
- `Dockerfile`: Docker configuration for building the Spring Boot application.
- `docker-compose.yml`: Docker Compose configuration for running the application with PostgreSQL.

## Getting Started

### 1. Download the Repository
### 2. Setup configurations
The application uses environment variables defined in the docker-compose.yml file to configure the database connection. These variables are passed to the Spring Boot application at runtime.
### 3. Build and Start the Application
Use Docker Compose to build the Docker images and start the application along with PostgreSQL.
```shell
docker-compose up --build
```

## Access the Application
The application will be accessible at http://localhost:8080.

## API Documentation
The API documentation for the Shopping Cart application can be found at: http://localhost:8080/swagger-ui/index.html

