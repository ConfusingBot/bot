package de.confusingbot.commands.cmds.admincmds.messagecommand;

import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
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

            //Get Message
            String message = " ";
            message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            if (message.isEmpty() || message.equals("")) message = " ";

            //Get Title
            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            if (title.isEmpty() || title.equals("")) title = " ";

            //Get Color
            Color color = Color.yellow;
            String colorCode = MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, false);
            if (!colorCode.isEmpty() || title.equals(""))
            {
                color = Color.decode(colorCode);
                if (color == null)
                    color = Color.yellow;
            }

            //Replace word placeholder
            message = replaceWordWithWordInText(MessageManager.NewMemberKeyWord, event.getMember().getAsMention(), message);

            //Message
            TextChannel messageChannel = guild.getTextChannelById(channelid);
            if (messageChannel != null)
                EmbedManager.SendCustomEmbed(title, message, color, messageChannel, 0);
            else
                MessageManager.embeds.CouldNotFindMessageChannelError(guild.getDefaultChannel(), MessageManager.welcomeMessageKey);
        }

        //Private
        if (MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true) && !event.getUser().isBot())
        {
            //Message
            String message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true);
            if (message.isEmpty() || message == "") message = " ";

            //Title
            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true);
            if (title.isEmpty() || title == "") title = " ";

            //Color
            Color color = Color.yellow;
            String colorCode = MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey, true);
            if (!colorCode.isEmpty() || title.equals(""))
            {
                color = Color.decode(colorCode);
                if (color == null)
                    color = Color.yellow;
            }

            //Replace word placeholder
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

            //Message
            String message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            if (message.isEmpty() || message == "") message = " ";

            //Title
            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            if (title.isEmpty() || title == "") title = " ";

            //Color
            Color color = Color.yellow;
            String colorCode = MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.leaveMessageKey, false);
            if (!colorCode.isEmpty() || title.equals(""))
            {
                color = Color.decode(colorCode);
                if (color == null)
                    color = Color.yellow;
            }

            //Replace word placeholder
            message = replaceWordWithWordInText(MessageManager.LeaveMemberKeyWord, event.getMember().getAsMention(), message);

            //Message
            TextChannel messageChannel = guild.getTextChannelById(channelid);
            if (messageChannel != null)
                EmbedManager.SendCustomEmbed(title, message, color, messageChannel, 0);
            else
                MessageManager.embeds.CouldNotFindMessageChannelError(guild.getDefaultChannel(), MessageManager.leaveMessageKey);
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
