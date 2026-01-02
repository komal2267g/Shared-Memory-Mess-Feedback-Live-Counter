# Stage 1: Compile the Java code
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY src/ ./src/
RUN javac src/*.java -d .

# Stage 2: Runtime environment
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy compiled classes
COPY --from=builder /app/src ./src
# Copy the web folder for the UI
COPY web/ ./web/

# Create a data directory for the Shared Memory file
RUN mkdir -p /app/data

# Expose the port
EXPOSE 8080

# Start the server
CMD ["java", "src.WebServer"]