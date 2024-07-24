# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the WAR file into the container at /app
COPY Garage.war /app

# Run the WAR file
CMD ["java", "-jar", "Garage.war"]

# Expose port 8080
EXPOSE 8080
