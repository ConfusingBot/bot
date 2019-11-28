package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReactRolesListener
{

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        try
        {
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
                        Guild guild = event.getGuild();

                        //Add Role to member
                        guild.addRoleToMember(event.getMember(), guild.getRoleById(roleid)).queue();

                    }
                }
            }
        } catch (HierarchyException e)
        {
            //Error
            ReactRoleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel());
        }

    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        try
        {
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
                        Guild guild = event.getGuild();

                        //Remove Role from member
                        guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleid)).queue();
                    }
                }
            }
        } catch (HierarchyException e)
        {
            //Error
            ReactRoleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel());
        }
    }
}
