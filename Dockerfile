# Use an official Gradle image as a parent image
FROM gradle:7.4.2-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and configuration files
COPY gradle /app/gradle
COPY gradlew /app/gradlew
COPY build.gradle.kts /app/
COPY settings.gradle.kts /app/

# Copy the source code
COPY src /app/src

# Build the application
RUN ./gradlew build --no-daemon

# Use a slim OpenJDK 17 runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/build/libs/shopping-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
