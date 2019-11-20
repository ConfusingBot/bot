package main.de.confusingbot.commands.cmds.admincmds.clearcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements ServerCommand
{
    private Strings strings = new Strings();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- clear [comments number x]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.MESSAGE_MANAGE))
        {
            if (args.length == 2)
            {
                try
                {
                    int amount = Integer.parseInt(args[1]);
                    List<Message> messages = getMessages(channel, amount);
                    channel.purgeMessages(messages);

                    //Message
                   strings.SuccessfulRemovedXMessages(channel, messages);

                } catch (NumberFormatException e)
                {
                    //Error
                   strings.NoValidNumberError(channel, args[1]);
                }
            }
            else
            {
                //Usage
                strings.ClearUsage(channel);
            }
        }
        else
        {
            //Error
           strings.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<Message> getMessages(MessageChannel channel, int amount)
    {
        List<Message> messages = new ArrayList<>();
        int i = amount + 1;//because it clear also delete your message

        for (Message message : channel.getIterableHistory().cache(false))
        {
            if (!message.isPinned())
            {
                messages.add(message);
            }
            if (--i <= 0) break;
        }

        return messages;
    }
}
