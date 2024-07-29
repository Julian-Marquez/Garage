# Stage 1: Build the application
FROM openjdk:21-oracle AS build

# Set the working directory
WORKDIR /app


# Copy the application source code to the container
COPY . .

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Build the application, skipping tests
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Copy the WAR file from the build stage to the runtime stage
COPY --from=build Garage.war

# Run the WAR file
CMD ["java", "-jar", "Garage.war"]

# Expose port 8080
EXPOSE 8080
