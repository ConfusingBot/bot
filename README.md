# ConfusingBot

### Setup
- Add `TOKEN` with the wished TOKEN to the Environment Variables
- Add `TOP_GG_TOKEN` with the wished TOP_GG_TOKEN to the Environment Variables
- Run `mvn install`
- Run Bot 🎉

### Build
- Run `mvn clean compile assembly:single` to build the ConfusingBot with dependencies
- Run `java -jar target/ConfusingBot-YOUR_VERSION.jar` to execute the ConfusingBot

### Deploy (https://dashboard.heroku.com/apps/confusing-bot/)
- Install the Heroku CLI and add it to the environment variables
- Run `mvn clean compile assembly:single heroku:deploy` to deploy the ConfusingBot to Heroku
- Run `heroku logs --app confusing-bot` to see the logs



