package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcceptRulesListener
{

    SQL sql = new SQL();

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();

            if (sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    String emote = "";

                    if (event.getReactionEmote().isEmoji())
                    {
                        emote = event.getReactionEmote().getEmoji();
                    }
                    else
                    {
                        emote = event.getReactionEmote().getId();
                    }

                    Guild guild = event.getGuild();
                    long rolenotacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong(), channelid, messageid, emote);
                    long roleacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong(), channelid, messageid, emote);

                    guild.addRoleToMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                    List<Role> roleBorders = getRoleBorders(guild);
                    if (roleBorders != null)
                    {
                        for (int i = 0; i < roleBorders.size(); i++)
                        {
                            guild.addRoleToMember(event.getMember(), roleBorders.get(i)).queue();
                        }
                    }
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();

            if (sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    String emote = "";

                    if (event.getReactionEmote().isEmoji())
                    {
                        emote = event.getReactionEmote().getEmoji();
                    }
                    else
                    {
                        emote = event.getReactionEmote().getId();
                    }

                    Guild guild = event.getGuild();
                    long rolenotacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong(), channelid, messageid, emote);
                    long roleacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong(), channelid, messageid, emote);

                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                    guild.addRoleToMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                    List<Role> roleBorders = getRoleBorders(guild);
                    if (roleBorders != null)
                    {
                        for (int i = 0; i < roleBorders.size(); i++)
                        {
                            guild.removeRoleFromMember(event.getMember(), roleBorders.get(i)).queue();
                        }
                    }

                }
            }
        }
    }

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        long rolenotacceptedid = sql.getRoleNotAcceptedID(guildid);

        if (rolenotacceptedid != -1)
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();

            //Add member accept rolerole
            addMemberRole(guild, member, rolenotacceptedid);
        }
    }

    //Helper
    private static List<Role> getRoleBorders(Guild guild)
    {
        List<Role> roleBorders = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            while (set.next())
            {
                Role role = guild.getRoleById(set.getLong("roleid"));
                roleBorders.add(role);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleBorders;
    }

    private void addMemberRole(Guild guild, Member member, long roleid)
    {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }
}
