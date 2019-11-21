package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.awt.*;

public class MessageListener
{

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        Guild guild = event.getGuild();

        long channelid = MessageManager.sql.GetChannelIDFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
        String message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
        String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);

        TextChannel messageChannel = guild.getTextChannelById(channelid);
        if (messageChannel != null)
        {
            EmbedManager.SendCustomEmbed(title, message, Color.ORANGE, messageChannel, 0);
        }
        else
        {
            //CouldNotFindMessageChannelError
        }

    }

}
