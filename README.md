# ConfusingBot

### Setup
- Add `TOKEN` with the wished TOKEN to the Environment Variables
- Add `TOP_GG_TOKEN` with the wished TOP_GG_TOKEN to the Environment Variables
- (Run `mvn -DskipTests clean dependency:list install` to build the ConfusingBot without dependencies)
- Run `mvn clean compile assembly:single` to build the ConfusingBot with dependencies
- Run `java -jar target/ConfusingBot-YOUR_VERSION.jar` to execute the ConfusingBot