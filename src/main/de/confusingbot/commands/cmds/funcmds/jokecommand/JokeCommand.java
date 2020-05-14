package main.de.confusingbot.commands.cmds.funcmds.jokecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.person.Person;
import main.de.confusingbot.manage.person.PersonManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class JokeCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public JokeCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- joke [category]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            //Random Color
            Random random = new Random();
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);

            int jsonType;
            String url;
            Person person = PersonManager.getPerson("patrick");
            if (args.length > 1)
            {
                switch (args[1])
                {
                    case "programming":
                        jsonType = 0;
                        url = "https://official-joke-api.appspot.com/jokes/programming/random";
                        break;

                    case "chucknorris":
                        jsonType = 1;
                        url = "https://api.chucknorris.io/jokes/random";
                        person = PersonManager.getPerson("chucknorris");
                        break;

                    case "momma":
                        jsonType = 2;
                        url = "https://api.yomomma.info/";
                        person = PersonManager.getPerson("günter");
                        break;

                    default:
                        embeds.JokeUsage(channel);
                        return;
                }
            }
            else
            {
                jsonType = 0;
                url = "https://official-joke-api.appspot.com/jokes/general/random";
            }

            //Send WaitMessage
            long waitMessageId = embeds.SendWaitMessage(channel);

            try
            {
                //Check Url
                URL jokeUrl = new URL(url);
                HttpURLConnection huc = (HttpURLConnection) jokeUrl.openConnection();
                int responseCode = huc.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    //Get JSON-Object
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(jokeUrl.openConnection().getInputStream()));
                    String jsonString = bufferedReader.readLine();
                    if (jsonType == 0)
                        jsonString = jsonString.replace("[", "").replace("]", "");
                    JSONObject jsonObject = new JSONObject(jsonString);

                    //Get Joke
                    String setup = null;
                    String punchline = null;

                    switch (jsonType)
                    {
                        case 0:
                            setup = jsonObject.getString("setup");
                            punchline = jsonObject.getString("punchline");
                            break;
                        case 1:
                            punchline = jsonObject.getString("value");
                            break;
                        case 2:
                            punchline = jsonObject.getString("joke");

                        default:
                            //Shouldn't happen

                    }

                    //Message
                    embeds.SendJoke(channel, setup, punchline, colorCode, person);
                }
                else
                {
                    //Error
                    embeds.SendSomethingWentWrong(channel, responseCode);
                }
            } catch (Exception e)
            {
                //Error
                embeds.SendSomethingWentWrong(channel, 0);
            }

            //Delete WaitMessage
            EmbedManager.DeleteMessageByID(channel, waitMessageId);
        }
    }
}
