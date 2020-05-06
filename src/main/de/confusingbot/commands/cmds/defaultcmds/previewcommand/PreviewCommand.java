package main.de.confusingbot.commands.cmds.defaultcmds.previewcommand;

import com.sun.tools.jconsole.JConsoleContext;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class PreviewCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public PreviewCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //- preview #hexColor [text]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        String embedMessage = "";
        String embedTitle = " ";
        boolean showName = true;
        String hideNamePrefix = "hide";

        if (args.length > 1)
        {
            Color color = Color.decode("#EB974E");
            int startIndex = 1;

            //Get Color
            String colorString = args[1];
            if (colorString.startsWith("#") && CommandsUtil.isColor(colorString))
            {
                startIndex = 2;
                color = Color.decode(colorString);
            }

            //Build String
            String wholeString = "";
            for (int i = startIndex; i < args.length; i++)
            {
                wholeString += (args[i] + " ");
            }

            //Get Title
            String[] parts = wholeString.split("MESSAGE:");
            if (parts.length > 1)
            {
                embedTitle = parts[0];
                embedMessage = parts[1];
            }
            else
            {
                embedMessage = parts[0];
            }

            //ShowName
            if (args[args.length - 1].equals(hideNamePrefix))
            {
                showName = false;
                embedMessage = embedMessage.substring(0, embedMessage.length() - 1 - hideNamePrefix.length());
            }

            //Build Embed
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(embedTitle);
            builder.setDescription(embedMessage);
            builder.setColor(color);

            if (showName)
                builder.setAuthor(member.getEffectiveName());

            //Send Embed
            channel.sendMessage(builder.build()).queue();
        }
        else
        {
            //Usage
            embeds.PreviewUsage(channel);
        }


    }
}
