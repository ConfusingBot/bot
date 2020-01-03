package main.de.confusingbot.commands.cmds.ownercmds.listcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


public class ListServerCommand implements ServerCommand
{

    main.de.confusingbot.listener.commandlistener.Embeds commandsListenerEmbeds = new main.de.confusingbot.listener.commandlistener.Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (member.getUser().getName().equals("ConfusingFutureGames"))
        {
            if (args.length == 1)
            {
                //GetServer
                Main.INSTANCE.shardManager.getShards().forEach(jda -> {
                    String tempString = "";
                    for (Guild guild : jda.getGuilds())
                    {
                        guild.getDefaultChannel().createInvite().queue(invite -> {
                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setColor(Color.pink);
                            builder.setTitle(guild.getName());
                            builder.addField("ID", "" + guild.getIdLong(), false);
                            builder.addField("Invite", invite.getUrl(), false);

                            //SendEmbed
                            EmbedManager.SendEmbed(builder, channel, 10);
                        });
                    }
                });
            }
        }
        else
        {
            //UnknownCommand Information
            commandsListenerEmbeds.CommandDoesNotExistsInformation(channel);
        }
    }


}
