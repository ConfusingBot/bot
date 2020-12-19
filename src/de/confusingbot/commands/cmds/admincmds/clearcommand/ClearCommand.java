package de.confusingbot.commands.cmds.admincmds.clearcommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class ClearCommand implements ServerCommand
{
    private Embeds embeds = ClearCommandManager.embeds;

    public ClearCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_MANAGE = Permission.MESSAGE_MANAGE;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- clear [comments number x]

        String[] args = CommandsUtil.messageToArgs(message);
        int maxClearNumber = 999;

        if (member.hasPermission(channel, ClearCommandManager.permission))
        {
            if (bot.hasPermission(channel, MESSAGE_MANAGE) && bot.hasPermission(channel, MESSAGE_WRITE))
            {
                if (args.length == 2)
                {
                    if (CommandsUtil.isNumeric(args[1]))
                    {
                        long amount = Long.parseLong(args[1]);
                        if (amount <= 999)
                        {
                            if (amount > 0)
                            {
                                List<Message> messages = getMessagesToDelete(channel, amount + 1);

                                channel.purgeMessages(messages);

                                //Message
                                embeds.SuccessfulRemovedXMessages(channel, messages);
                            }
                            else
                            {
                                //Error
                              embeds.NegativeNumberError(channel);
                            }
                        }
                        else
                        {
                            //Information
                           embeds. MaxNumberInformation(channel, maxClearNumber);
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
                EmbedManager.SendNoPermissionEmbed(channel, MESSAGE_MANAGE, "");
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel, ClearCommandManager.permission);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<Message> getMessagesToDelete(MessageChannel channel, long amount)
    {
        List<Message> messages = new ArrayList<>();
        long i = amount;

        for (Message message : channel.getIterableHistory().cache(false))
        {
            if (!message.isPinned())
            {
                messages.add(message);
                if (--i <= 0) break;
            }
        }
        return messages;
    }
}
