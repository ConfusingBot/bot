package main.de.confusingbot.commands.cmds.defaultcmds.pingcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class PingCommand implements ServerCommand
{
    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        message.delete().queue();

        long time = System.currentTimeMillis();
        channel.sendMessage("Pong\uD83C\uDFD3\n**") /* => RestAction<Message> */
                .queue(response /* => Message */ -> {
                    response.editMessageFormat("Pong\uD83C\uDFD3 %d ms", System.currentTimeMillis() - time).queue();
                });
    }
}
