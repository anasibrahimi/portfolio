# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-17-alpine AS build
WORKDIR /app

# Copy the Maven wrapper and POM file
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Download dependencies (this layer will be cached unless pom.xml changes)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src/ ./src/

# Build the application (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Create directory for file uploads
RUN mkdir -p /app/uploads

# Copy the built WAR file from the build stage
COPY --from=build /app/target/portfolio-0.0.1-SNAPSHOT.war ./app.war

# Set environment variables (these will be overridden by actual env vars at runtime)
ENV SPRING_PROFILES_ACTIVE=prod \
    DB_URL=jdbc:mysql://db:3306/portfolio \
    DB_DRIVER=com.mysql.cj.jdbc.Driver \
    DB_USERNAME=root \
    DB_PASSWORD=password \
    DB_DIALECT=org.hibernate.dialect.MySQLDialect \
    FILE_UPLOAD_DIR=/app/uploads \
    SERVER_PORT=8080

# Expose the port the app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.war"]
