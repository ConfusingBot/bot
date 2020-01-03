package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
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
        //- event create [#channel] [messageid] [eventName] [color] [time] [eventRole] [takePartEmote]
        //- event remove [@eventRole]
        //- event announcement [#channel] [@eventRole] [Title] MESSAGE: [Message]
        //- event vote

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

    }
}
