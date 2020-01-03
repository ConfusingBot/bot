package main.de.confusingbot.commands.cmds.defaultcmds.pingcommand;

import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class PingCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public PingCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        long time = System.currentTimeMillis();
        channel.sendMessage("Pong\uD83C\uDFD3\n**")
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Pong\uD83C\uDFD3 %d ms", System.currentTimeMillis() - time).queue();
                });
    }
}
