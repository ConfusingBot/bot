package de.confusingbot.listener.commandlistener;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void CommandDoesNotExistsInformation(TextChannel channel)
    {
        Member bot = channel.getGuild().getSelfMember();

        if (bot.hasPermission(channel, Permission.MESSAGE_WRITE))
            channel.sendMessage("`Unknown Comment!`").queue();
    }

    public void CommandDoesNotExistsPrivateInformation(PrivateChannel channel)
    {
        channel.sendMessage("`Unknown Comment!`\n **Note:** Only the help command works here!").queue();
    }
}
