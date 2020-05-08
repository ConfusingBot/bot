package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReactRolesListener
{

    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;
    Permission MESSAGE_MANAGE = Permission.MESSAGE_MANAGE;

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();
        Member member = event.getMember();

        if (event.getChannelType() == ChannelType.TEXT && member != null)
        {
            long guildid = event.getGuild().getIdLong();
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();

            if (ReactRoleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                TextChannel channel = guild.getTextChannelById(channelid);
                if (!event.getUser().isBot())
                {
                    if (bot.hasPermission(MANAGE_ROLES))
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

                        long roleid = ReactRoleManager.sql.GetRoleIdFromSQL(guildid, channelid, messageid, emote);
                        Role role = guild.getRoleById(roleid);

                        if (role != null)
                        {
                            //Add Role to member
                            CommandsUtil.AddOrRemoveRoleFromMember(guild, member, role, true);
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.RoleDoesNotExistError(event.getTextChannel(), roleid);

                            //SQL
                            ReactRoleManager.sql.removeFromSQL(guildid, channelid, messageid, emote, roleid);

                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(channel == null ? guild.getDefaultChannel() : channel, MANAGE_ROLES, "ReactRoleCommand | Can't add role to member!");
                    }
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();
        Member member = event.getMember();

        if (event.getChannelType() == ChannelType.TEXT && member != null)
        {
            long guildid = event.getGuild().getIdLong();
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();

            if (ReactRoleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                TextChannel channel = guild.getTextChannelById(channelid);
                if (!event.getUser().isBot())
                {
                    if (bot.hasPermission(MANAGE_ROLES))
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

                        long roleid = ReactRoleManager.sql.GetRoleIdFromSQL(guildid, channelid, messageid, emote);
                        Role role = guild.getRoleById(roleid);

                        if (role != null)
                        {
                            CommandsUtil.AddOrRemoveRoleFromMember(guild, member, role, false);
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.RoleDoesNotExistError(event.getTextChannel(), roleid);

                            //SQL
                            ReactRoleManager.sql.removeFromSQL(guildid, channelid, messageid, emote, roleid);
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(channel == null ? guild.getDefaultChannel() : channel, MANAGE_ROLES, "ReactRoleCommand | Can't remove role from member!");
                    }
                }
            }
        }
    }

    public void onMemberLeaveListener(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();
        ResultSet set = ReactRoleManager.sql.GetReactRolesResultSet(guild.getIdLong());

        try
        {
            while (set.next())
            {
                long channelID = set.getLong("channelid");
                long messageID = set.getLong("messageid");

                TextChannel channel = guild.getTextChannelById(channelID);
                Message message = CommandsUtil.getLatestesMessageByID(channel, messageID);

                if (message != null)
                {
                    if (bot.hasPermission(channel, MESSAGE_MANAGE))
                    {
                        List<MessageReaction> reactions = message.getReactions();

                        for (MessageReaction reaction : reactions)
                        {
                            User user = event.getUser();
                            if (user != null)
                            {
                                try
                                {
                                    message.removeReaction(reaction.getReactionEmote().getEmote(), user).queue();
                                } catch (IllegalStateException e)
                                {
                                    message.removeReaction(reaction.getReactionEmote().getEmoji(), user).queue();
                                }
                            }
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(channel, MESSAGE_MANAGE, "ReactRoleCommand | Can't remove reaction from the member who left the server!");
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
