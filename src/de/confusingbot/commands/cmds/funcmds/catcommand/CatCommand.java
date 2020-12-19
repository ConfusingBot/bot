package de.confusingbot.commands.cmds.funcmds.catcommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.json.JsonReader;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.person.Person;
import de.confusingbot.manage.person.PersonManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

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
                //Send WaitMessage
                long waitMessageId = embeds.SendWaitMessage(channel);

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
                        JSONObject jsonObject = JsonReader.readJsonFromUrl(catUrl);

                        //Get Data
                        String imageUrl = jsonObject.getString("file");

                        //Message
                        embeds.SendCat(channel, imageUrl, person, colorCode);
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
            else
            {
                //Usage
                embeds.CatUsage(channel);
            }

        }
    }
}
