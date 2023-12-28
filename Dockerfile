# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory in the container
WORKDIR ./

# Copy the JAR file into the container at /app
COPY build/libs/voting-data-manager-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app will run on
EXPOSE 8080

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app.jar"]
