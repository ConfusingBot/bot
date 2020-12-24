# ConfusingBot
🤖 This Bot might be Confusing but this bot has many useful features.. which upgrade your server and simplify the daily life on it! |
_Note: This Bot isn't supported anymore and the code is pretty dirty since I learned with it the main concepts of Java!_

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



