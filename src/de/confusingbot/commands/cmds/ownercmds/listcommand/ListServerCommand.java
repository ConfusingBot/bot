package de.confusingbot.commands.cmds.ownercmds.listcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.listener.commandlistener.Embeds;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;


public class ListServerCommand implements ServerCommand
{

    Embeds commandsListenerEmbeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());
        boolean isAdmin = member.getUser().getId() == "637931838052237312" || member.getUser().getId().toString() == "333341131053989890";

        if (isAdmin)
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
