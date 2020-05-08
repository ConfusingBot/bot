package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;

import java.awt.*;

public class MessageListener
{

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        Guild guild = event.getGuild();

        if (MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false))
        {
            long channelid = MessageManager.sql.GetChannelIDFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            String message = " ";
            message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            if (message.isEmpty() || message == "") message = " ";

            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            if (title.isEmpty() || title == "") title = " ";

            Color color = Color.decode(MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false));
            if (color == null) color = Color.yellow;

            message = replaceWordWithWordInText(MessageManager.NewMemberKeyWord, event.getMember().getAsMention(), message);

            TextChannel messageChannel = guild.getTextChannelById(channelid);
            if (messageChannel != null)
            {
                EmbedManager.SendCustomEmbed(title, message, color, messageChannel, 0);
            }
            else
            {
                MessageManager.embeds.CouldNotFindMessageChannelError(guild.getDefaultChannel(), MessageManager.welcomeMessageKey);
            }
        }

        //Private
        if (MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true) && !event.getUser().isBot())
        {
            String message = " ";
            message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true);
            if (message.isEmpty() || message == "") message = " ";

            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true);
            if (title.isEmpty() || title == "") title = " ";

            Color color = Color.decode(MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true));
            if (color == null) color = Color.yellow;

            message = replaceWordWithWordInText(MessageManager.NewMemberKeyWord, event.getMember().getAsMention(), message);

            //Send Private Message
            EmbedManager.SendCustomPrivateEmbed(title, message, color, event.getUser());
        }
    }

    public void onMemberLeaveEvent(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();

        if (MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false))
        {
            long channelid = MessageManager.sql.GetChannelIDFormSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            String message = "";
            message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            String title = "";
            title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            Color color = Color.decode(MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false));

            message = replaceWordWithWordInText(MessageManager.LeaveMemberKeyWord, event.getMember().getAsMention(), message);

            TextChannel messageChannel = guild.getTextChannelById(channelid);
            if (messageChannel != null)
            {
                EmbedManager.SendCustomEmbed(title, message, color, messageChannel, 0);
            }
            else
            {
                MessageManager.embeds.CouldNotFindMessageChannelError(guild.getDefaultChannel(), MessageManager.leaveMessageKey);
            }
        }
    }

    private String replaceWordWithWordInText(String keyWord, String newWord, String text)
    {
        String[] words = text.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word : words)
        {
            if (word.equals(keyWord))
            {
                word = newWord;
            }
            builder.append(word + " ");
        }
        return builder.toString().trim();
    }
}
