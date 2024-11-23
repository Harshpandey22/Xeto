# Stage 1: Builder
FROM eclipse-temurin:11-jdk-jammy as builder
WORKDIR /opt/app

# Copy Maven wrapper and project descriptor files
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Ensure the Maven wrapper has executable permissions
RUN chmod +x mvnw

# Download project dependencies for offline use
RUN ./mvnw dependency:go-offline

# Copy project source files
COPY ./src ./src

# Build the project and skip tests
RUN ./mvnw clean install -DskipTests -X  # Added -X for more verbose output in case of errors

# Stage 2: Runtime
FROM eclipse-temurin:11-jre-jammy
WORKDIR /opt/app
EXPOSE 8080

# Copy the built jar file from the builder stage
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/opt/app/app.jar"]
