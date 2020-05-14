package main.de.confusingbot.commands.cmds.funcmds.yodaCommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.json.JsonReader;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;

public class YodaCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public YodaCommand()
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
            if (args.length > 1)
            {
                //Send WaitMessage
                long waitMessageId = embeds.SendWaitMessage(channel);

                //Get Text
                String text = "";
                String wordSeparator = "%20";
                for (int i = 1; i < args.length; i++)
                {
                    text += args[i] + wordSeparator;
                }
                text = text.substring(0, text.length() - wordSeparator.length());
                text = text.replace("ä", "ae");
                text = text.replace("ö", "oe");
                text = text.replace("ü", "ue");

                try
                {
                    //Check Url
                    URL yodaUrl = new URL("http://yoda-api.appspot.com/api/v1/yodish?text=" + text);
                    HttpURLConnection huc = (HttpURLConnection) yodaUrl.openConnection();
                    int responseCode = huc.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //Get JSON-Object
                        JSONObject jsonObject = JsonReader.readJsonFromUrl(yodaUrl);

                        //Get Data
                        String yodaText = jsonObject.getString("yodish");

                        //Message
                        embeds.SendYodaText(channel, yodaText);
                    }
                    else
                    {
                        if (responseCode == 500)
                        {
                           embeds.SendNoValidLetterWasUsed(channel);
                        }
                        else
                        {
                            //Error
                            embeds.SendSomethingWentWrong(channel, responseCode);
                        }
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
                embeds.YodaUsage(channel);
            }
        }
    }
}
