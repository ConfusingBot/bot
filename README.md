# ConfusingBot
🤖 This Bot might be Confusing but this bot has many useful features.. 
which upgrade your server and simplify the daily life on it! \
_Note: This Bot isn't supported anymore and the code is pretty dirty since I learned with it the main concepts of Java!_

### Setup
`.env.local`
```
DISCORD_TOKEN={DISCORD_TOKEN}
TOP_GG_TOKEN={TOP_GG_TOKEN}
# db as this is the internal reference to the 'db' container (https://stackoverflow.com/questions/50927680/how-to-access-postgres-docker-container-other-docker-container-without-ip-addres)
DB_URI=postgresql://confusing-db:5401/confusing_bot
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

### Build
- Run `mvn -DskipTests clean dependency:list install` to build the ConfusingBot with dependencies
- Run `java -jar target/ConfusingBot-[YOUR_VERSION]-jar-with-dependencies.jar` to execute the ConfusingBot

### Deploy (https://dashboard.heroku.com/apps/confusing-bot/)
- Install the Heroku CLI and add it to the environment variables
- Run `mvn clean compile assembly:single heroku:deploy` to deploy the ConfusingBot to Heroku
- Run `heroku logs --app confusing-bot` to see the logs

### Heroku
Commands that Heroku executes to execute the Java Project:
- Build `mvn -DskipTests clean dependency:list install`
- Start `defined in Procfile`

Connect to psql database
```
heroku pg:psql --app confusing-bot
```


