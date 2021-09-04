# Use the OpenJDK 11 image as the base image
FROM openjdk:11

# Create a new app directory for my application files
RUN mkdir /app

# Copy the app files from host machine to image filesystem
COPY target/ConfusingBot-0.0.5-jar-with-dependencies.jar /app

# Set the directory for executing future commands
WORKDIR /app

# Run the .jar file
CMD java -jar ConfusingBot-0.0.5-jar-with-dependencies.jar
