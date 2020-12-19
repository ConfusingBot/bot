package de.confusingbot.commands.cmds.ownercmds.sendcommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.listener.commandlistener.Embeds;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.*;

import java.util.Arrays;

public class SendCommand implements ServerCommand
{
    Embeds commandsListenerEmbeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());
        boolean isAdmin = member.getUser().getId().equals("637931838052237312") || member.getUser().getId().equals("333341131053989890");

        if (isAdmin)
        {
            if (args.length > 1)
            {
                //Message
                EmbedManager.SendMessage(String.join(" ", Arrays.copyOfRange(args, 1, args.length)), channel, 0);
            }
        }
        else
        {
            //UnknownCommand Information
            commandsListenerEmbeds.CommandDoesNotExistsInformation(channel);
        }
    }
}
