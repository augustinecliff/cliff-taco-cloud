# Use a base image that supports Java 18
FROM openjdk:18-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your target directory to the container
COPY target/taco-cloud-deployment-0.0.1-SNAPSHOT.jar taco-cloud-deployment.jar

# Expose the port your application listens on
EXPOSE 9090

# Specify the command to run when the container starts
CMD ["java", "-jar", "taco-cloud-deployment.jar", "--server.port=9090"]
