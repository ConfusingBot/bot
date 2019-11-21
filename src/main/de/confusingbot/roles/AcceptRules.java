package main.de.confusingbot.roles;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcceptRules {

    public static void onReactionAdd(MessageReactionAddEvent event) {

        if (event.getChannelType() == ChannelType.TEXT) {
            if (!event.getUser().isBot()) {

                long channelid = event.getChannel().getIdLong();
                long messageid = event.getMessageIdLong();
                String emote = "";

                if (event.getReactionEmote().isEmoji()) {
                    emote = event.getReactionEmote().getEmoji();
                } else {
                    emote = event.getReactionEmote().getId();
                }

                ResultSet set = LiteSQL.onQuery("SELECT * FROM acceptrules WHERE "
                        + "guildid = " + event.getGuild().getIdLong()
                        + " AND channelid = " + channelid
                        + " AND messageid = " + messageid
                        + " AND emote = '" + emote + "'");

                try {
                    if (set.next()) {
                        Guild guild = event.getGuild();

                        long rolenotacceptedid = set.getLong("rolenotacceptedid");
                        long roleacceptedid = set.getLong("roleacceptedid");

                        guild.addRoleToMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                        guild.removeRoleFromMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                        List<Role> roleBorders = getRoleBorders(guild);
                        if (roleBorders != null) {
                            for (int i = 0; i < roleBorders.size(); i++) {
                                guild.addRoleToMember(event.getMember(), roleBorders.get(i)).queue();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void onReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getChannelType() == ChannelType.TEXT) {
            if (!event.getUser().isBot()) {

                long channelid = event.getChannel().getIdLong();
                long messageid = event.getMessageIdLong();
                String emote = "";

                if (event.getReactionEmote().isEmoji()) {
                    emote = event.getReactionEmote().getEmoji();
                } else {
                    emote = event.getReactionEmote().getId();
                }

                ResultSet set = LiteSQL.onQuery("SELECT * FROM acceptrules WHERE "
                        + "guildid = " + event.getGuild().getIdLong()
                        + " AND channelid = " + channelid
                        + " AND messageid = " + messageid
                        + " AND emote = '" + emote + "'");

                try {
                    if (set.next()) {
                        Guild guild = event.getGuild();

                        long rolenotacceptedid = set.getLong("rolenotacceptedid");
                        long roleacceptedid = set.getLong("roleacceptedid");

                        guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                        guild.addRoleToMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                        List<Role> roleBorders = getRoleBorders(guild);
                        if (roleBorders != null) {
                            for (int i = 0; i < roleBorders.size(); i++) {
                                guild.removeRoleFromMember(event.getMember(), roleBorders.get(i)).queue();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Helper
    private static List<Role> getRoleBorders(Guild guild) {
        List<Role> roleBorders = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guild.getIdLong());

        try {
            while (set.next()) {
                Role role = guild.getRoleById(set.getLong("roleid"));
                roleBorders.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleBorders;
    }

}
