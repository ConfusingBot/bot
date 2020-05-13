package main.de.confusingbot.commands.cmds.defaultcmds.catcommand;

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

public class CatCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public CatCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length == 1)
            {
                //Get Random Color
                Random random = new Random();
                int nextInt = random.nextInt(0xffffff + 1);
                String colorCode = String.format("#%06x", nextInt);

                //Person
                Person person = PersonManager.getPerson("grandma");

                try
                {
                    //Check Url
                    URL catUrl = new URL("https://aws.random.cat/meow?ref=apilist.fun");
                    HttpURLConnection huc = (HttpURLConnection) catUrl.openConnection();
                    int responseCode = huc.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //Get JSON-Object
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(catUrl.openConnection().getInputStream()));
                        String jsonString = bufferedReader.readLine();
                        JSONObject jsonObject = new JSONObject(jsonString);

                        //Get Data
                        String imageUrl = jsonObject.getString("file");

                        //Message
                        embeds.SendCat(channel, imageUrl, person, colorCode);
                    }
                    else
                    {
                        //Error
                        embeds.SendSomethingWentWrong(channel);
                    }

                } catch (Exception e)
                {
                    //Error
                    embeds.SendSomethingWentWrong(channel);
                }
            }
            else
            {
                //Usage
                embeds.CatUsage(channel);
            }

        }
    }
}
