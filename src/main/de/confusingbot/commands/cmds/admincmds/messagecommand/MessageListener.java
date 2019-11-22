package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MessageListener {

    public void onMemberJoinListener(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();

        if (MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.welcomeMessageKey)) {
            long channelid = MessageManager.sql.GetChannelIDFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
            String message = MessageManager.sql.GetMessageFormSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
            String title = MessageManager.sql.GetTitleFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
            Color color = Color.decode(MessageManager.sql.GetColorFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey));


            TextChannel messageChannel = guild.getTextChannelById(channelid);
            if (messageChannel != null) {
                EmbedManager.SendCustomEmbed(title, getMentionableMessage(message, guild), color, messageChannel, 0);
            } else {
                MessageManager.embeds.CouldNotFindMessageChannelError(guild.getDefaultChannel(), MessageManager.welcomeMessageKey);
            }
        }
    }

    private String getMentionableMessage(String message, Guild guild) {
        String newMessage = "";
        String[] words = message.split(" ");

        List<Long> mentionedRoleIDs = MessageManager.sql.GetMentionedRolesFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
        List<Role> roles = getMentionedRoles(mentionedRoleIDs, guild);
        List<Long> mentionedChannelIDs = MessageManager.sql.GetMentionedChannelsFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);
        List<TextChannel> channels = getMentionedChannels(mentionedChannelIDs, guild);

        StringBuilder builder = new StringBuilder();
        for(String word : words){
            for(Role role : roles) {
                if(word.contains(role.getName())){
                    word = role.getAsMention();
                }
            }

            for(TextChannel channel : channels){
                if(word.contains(channel.getName())){
                    word = channel.getAsMention();
                }
            }
            builder.append(word);
        }
        newMessage = builder.toString().trim();

        return newMessage;
    }

    private List<Role> getMentionedRoles(List<Long> mentionedRoleIDs, Guild guild) {
        List<Role> roles = new ArrayList<>();

        for (long mentionedRoleID : mentionedRoleIDs) {
            try {
                roles.add(guild.getRoleById(mentionedRoleID));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return roles;
    }

    private List<TextChannel> getMentionedChannels(List<Long> mentionedChannelIDs, Guild guild) {
        List<TextChannel> textChannels = new ArrayList<>();

        for (long mentionedChannelID : mentionedChannelIDs) {
            try {
                textChannels.add(guild.getTextChannelById(mentionedChannelID));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return textChannels;
    }

}
