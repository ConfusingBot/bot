package de.confusingbot.commands.cmds.defaultcmds.pingcommand;

import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PingCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public PingCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong\uD83C\uDFD3\n**")
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong\uD83C\uDFD3 %d ms", System.currentTimeMillis() - time).queue();
                    });
        }
    }
}
