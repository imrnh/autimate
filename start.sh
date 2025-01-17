#!/bin/bash

# Navigate to backend directory
cd /app/backend

# Run the generated JAR file
echo "Starting the backend..."
java -jar target/*.jar &

# Navigate to frontend directory
cd /app/frontend

# Install dependencies (if not installed)
if [ ! -d "node_modules" ]; then
    echo "Installing frontend dependencies..."
    npm install
fi

# Start the frontend application within the correct directory
echo "Starting the frontend..."
exec npm run dev --host
