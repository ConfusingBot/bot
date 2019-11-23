package main.de.confusingbot.commands.cmds.admincmds.clearcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements ServerCommand
{
    private Embeds embeds = new Embeds();

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
                if (CommandsUtil.isNumeric(args[1]))
                {
                    long amount = Long.parseLong(args[1]);
                    if (amount > 0)
                    {
                        List<Message> messages = getMessagesToDelete(channel, amount);
                        channel.purgeMessages(messages);

                        //Message
                        embeds.SuccessfulRemovedXMessages(channel, messages);
                    }
                    else
                    {
                        channel.sendMessage("Really?").queue();
                    }
                }
                else
                {
                    //Error
                    embeds.NoValidNumberError(channel, args[1]);
                }
            }
            else
            {
                //Usage
                embeds.ClearUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<Message> getMessagesToDelete(MessageChannel channel, long amount)
    {
        CommandsUtil.sleepXSeconds(0.5f);
        List<Message> messages = new ArrayList<>();
        long i = amount;

        for (Message message : channel.getIterableHistory().cache(false))
        {
            if (!message.isPinned() && !message.getMember().getUser().isBot())
            {
                messages.add(message);
                if (--i <= 0) break;
            }
        }
        return messages;
    }
}
