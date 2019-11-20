package main.de.confusingbot.roles;

import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReactRoles {

    public static void onReactionAdd(MessageReactionAddEvent event) {
        try {

            if (event.getChannelType() == ChannelType.TEXT) {
                if (!event.getUser().isBot()) {

                    long guildid = event.getGuild().getIdLong();
                    long channelid = event.getChannel().getIdLong();
                    long messageid = event.getMessageIdLong();
                    String emote = "";

                    if (event.getReactionEmote().isEmoji()) {
                        emote = event.getReactionEmote().getEmoji();
                    } else {
                        emote = event.getReactionEmote().getId();
                    }

                    ResultSet set = LiteSQL.onQuery("SELECT roleid FROM reactroles WHERE "
                            + "guildid = " + guildid
                            + " AND channelid = " + channelid
                            + " AND messageid = " + messageid
                            + " AND emote = '" + emote + "'");

                    try {
                        if (set.next()) {
                            long roleid = set.getLong("roleid");

                            Guild guild = event.getGuild();
                            guild.addRoleToMember(event.getMember(), guild.getRoleById(roleid)).queue();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }

        } catch (HierarchyException e) {
            EmbedManager.SendErrorEmbed("Error", "The bot has no right to assign this role\n" +
                    "Please give the bot a role over the role to be assigned", event.getTextChannel(), 5);
        }

    }

    public static void onReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getChannelType() == ChannelType.TEXT) {
            if (!event.getUser().isBot()) {

                long guildid = event.getGuild().getIdLong();
                long channelid = event.getChannel().getIdLong();
                long messageid = event.getMessageIdLong();
                String emote = "";

                if (event.getReactionEmote().isEmoji()) {
                    emote = event.getReactionEmote().getEmoji();
                } else {
                    emote = event.getReactionEmote().getId();
                }

                ResultSet set = LiteSQL.onQuery("SELECT roleid FROM reactroles WHERE "
                        + "guildid = " + guildid
                        + " AND channelid = " + channelid
                        + " AND messageid = " + messageid
                        + " AND emote = '" + emote + "'");

                try {
                    if (set.next()) {
                        long roleid = set.getLong("roleid");

                        Guild guild = event.getGuild();
                        guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleid)).queue();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
