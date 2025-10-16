# Build stage
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /app

# Copy Maven wrapper and dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Make Maven wrapper executable
RUN chmod +x ./mvnw

# Download dependencies (cached layer)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/SmartTaskPlanner-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render will override with $PORT env variable)
EXPOSE 8081

# Add healthcheck (optional but recommended for Render)
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-8081}/actuator/health || exit 1

# Run the application
# Render injects PORT env variable, Spring Boot will use it automatically
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]