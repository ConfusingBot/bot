package main.de.confusingbot.commands.cmds.defaultcmds.previewcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PreviewCommand implements ServerCommand
{

    Strings strings = new Strings();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //- preview [text]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length > 1)
        {
            String userMessage = "";
            for (int i = 1; i < args.length; i++)
            {
                userMessage += (args[i] + " ");
            }

            EmbedBuilder builder = new EmbedBuilder();

            builder.setDescription(userMessage);
            builder.setColor(0xeb974e);

            channel.sendMessage(builder.build()).queue();
        }
        else
        {
            //Usage
            strings.PreviewUsage(channel);
        }


    }
}
