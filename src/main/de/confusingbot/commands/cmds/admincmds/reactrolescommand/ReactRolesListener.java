package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
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

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        Guild guild = event.getGuild();
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long guildid = event.getGuild().getIdLong();
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();
            if (ReactRoleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
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

                    long roleid = ReactRoleManager.sql.GetRoleIdFromSQL(guildid, channelid, messageid, emote);
                    Role role = guild.getRoleById(roleid);

                    if (role != null)
                    {

                        try
                        {
                            //Add Role to member
                            guild.addRoleToMember(event.getMember(), guild.getRoleById(roleid)).queue();
                        } catch (HierarchyException e)
                        {
                            //Error
                            ReactRoleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), role);
                        }
                    }
                    else
                    {
                        //Error
                        ReactRoleManager.embeds.RoleDoesNotExistError(event.getTextChannel(), roleid);

                        //SQL
                        ReactRoleManager.sql.removeFromSQL(guildid, channelid, messageid, emote, roleid);

                    }
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        Guild guild = event.getGuild();
        if (event.getChannelType() == ChannelType.TEXT)
        {
            Member member = event.getMember();
            if (member == null) return;

            long guildid = event.getGuild().getIdLong();
            long channelid = event.getChannel().getIdLong();
            long messageid = event.getMessageIdLong();
            if (ReactRoleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
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

                    long roleid = ReactRoleManager.sql.GetRoleIdFromSQL(guildid, channelid, messageid, emote);
                    Role role = guild.getRoleById(roleid);

                    if (role != null)
                    {
                        try
                        {
                            //Remove Role from member
                            guild.removeRoleFromMember(member, guild.getRoleById(roleid)).queue();
                        } catch (HierarchyException e)
                        {
                            //Error
                            ReactRoleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), role);
                        }
                    }
                    else
                    {
                        //Error
                        ReactRoleManager.embeds.RoleDoesNotExistError(event.getTextChannel(), roleid);

                        //SQL
                        ReactRoleManager.sql.removeFromSQL(guildid, channelid, messageid, emote, roleid);
                    }
                }
            }
        }
    }

    public void onMemberLeaveListener(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();
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
                    List<MessageReaction> reactions = message.getReactions();

                    for (MessageReaction reaction : reactions)
                    {
                        User user = event.getUser();
                        if (user != null)
                        {
                            try
                            {
                                message.removeReaction(reaction.getReactionEmote().getEmote(), user).queue();
                            }
                            catch (IllegalStateException e)
                            {
                                message.removeReaction(reaction.getReactionEmote().getEmoji(), user).queue();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
