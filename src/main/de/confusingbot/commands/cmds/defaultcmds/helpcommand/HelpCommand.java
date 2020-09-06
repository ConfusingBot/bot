package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.function.Consumer;

public class HelpCommand implements ServerCommand
{
    public HelpCommand()
    {
        HelpManager.insertHelp();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        sendEmbed(HelpManager.getHelpBuilder(args), member, channel);
    }

    private void sendEmbed(ArrayList<EmbedBuilder> builders, Member member, TextChannel channel)
    {
         //Callback for private HelpMessage
        Consumer<? super Throwable> callback = (response) -> {
            System.out.println("Failed to send private Message!");
            EmbedManager.SendInfoEmbed("Sry.. I can't send the HelpEmbed to you because I am not allowed to dm you :/", channel, 10);
        };
        member.getUser().openPrivateChannel().queue((privateChannel) -> {
            for (EmbedBuilder builder : builders)
                privateChannel.sendMessage(builder.build()).queue(null, callback);
        });
    }
}
