# Use Ubuntu 20.04 as base image
FROM ubuntu:20.04

# Set non-interactive mode for apt-get
ENV DEBIAN_FRONTEND=noninteractive

# Update and install required packages
RUN apt-get update && apt-get install -y \
    curl \
    wget \
    git \
    unzip \
    software-properties-common \
    && rm -rf /var/lib/apt/lists/*

# Install OpenJDK 21
RUN add-apt-repository ppa:linuxuprising/java -y && \
    apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    rm -rf /var/lib/apt/lists/*

# Install Node.js 22 and npm 10.8
RUN curl -fsSL https://deb.nodesource.com/setup_22.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g npm@10.8

# Set working directory
WORKDIR /app

# Copy project directories
COPY ai_fn /app/ai_fn
COPY backend /app/backend
COPY frontend /app/frontend

# Install Maven
RUN apt-get update && apt-get install -y maven

# Build and package the Spring Boot application
WORKDIR /app/backend
RUN mvn clean package -DskipTests

# Set working directory for frontend
WORKDIR /app/frontend
RUN npm install  # Install frontend dependencies

# Expose necessary ports (Spring Boot and frontend)
EXPOSE 8080 5173

# Start both backend and frontend using a script
WORKDIR /app
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

CMD ["/bin/bash", "/app/start.sh"]