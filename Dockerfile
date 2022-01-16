# https://stackoverflow.com/questions/27767264/how-to-dockerize-maven-project-and-how-many-ways-to-accomplish-it

#
# Build stage
#
FROM maven:3-jdk-11 AS build

# Create 'app' directory
# and specify it as working directory
RUN mkdir -p /app/build
WORKDIR /app/build

# Environment Variables
ENV APP_VERSION=0.0.5

# Install Dependencies
COPY pom.xml ./
RUN mvn clean install

# Build .jar file
COPY src ./src
RUN mvn -DskipTests clean dependency:list install

#
# Package stage
#
FROM openjdk:11-jdk

# Copy .jar file
RUN mkdir -p /app/target
COPY --from=build /app/build/target /app/target
# RUN rm /app/build
WORKDIR /app/target

# For debugging:
# Lists all the files and folder that we have copied inside the working directory.
# Note: To actually see the output of this command we need to specify the flag '--progress=plain '
RUN ls -l

# Run the .jar file
CMD ["java", "-jar", "ConfusingBot-0.0.5-jar-with-dependencies.jar"]
