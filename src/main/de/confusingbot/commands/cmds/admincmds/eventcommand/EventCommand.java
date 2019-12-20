package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class EventCommand implements ServerCommand
{
    SQL sql = new SQL();
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- event create [#channel] [messageid] [eventName] [time] [eventRole] [takePartEmote]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();


    }
}
