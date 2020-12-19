package de.confusingbot.commands.cmds.admincmds.eventcommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class EventCommandListener
{
    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();
            long channelid = event.getChannel().getIdLong();

            if (EventCommandManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
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

                        //Get Role Id
                        long roleID = EventCommandManager.sql.getRoleID(guild.getIdLong(), messageid, emote);
                        Role role = guild.getRoleById(roleID);

                        //Add Role
                        if (role != null)
                            CommandsUtil.AddOrRemoveRoleFromMember(guild, event.getMember(), role, true);
                        else
                        {
                            //SQL
                            EventCommandManager.sql.removeFormSQL(guild.getIdLong(), role.getIdLong());
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(channel == null ? guild.getDefaultChannel() : channel, MANAGE_ROLES, "EventCommand | Can't add role to member!");
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
        if (member != null)
        {
            if (event.getChannelType() == ChannelType.TEXT)
            {
                long messageid = event.getMessageIdLong();
                long channelid = event.getChannel().getIdLong();

                if (EventCommandManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
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

                            //Get Role Id
                            long roleID = EventCommandManager.sql.getRoleID(guild.getIdLong(), messageid, emote);
                            Role role = guild.getRoleById(roleID);

                            //Remove Role
                            if (role != null)
                                CommandsUtil.AddOrRemoveRoleFromMember(guild, event.getMember(), role, false);
                            else
                            {
                                //SQL
                                EventCommandManager.sql.removeFormSQL(guild.getIdLong(), role.getIdLong());
                            }
                        }
                        else
                        {
                            //Error
                            EmbedManager.SendNoPermissionEmbed(channel == null ? guild.getDefaultChannel() : channel, MANAGE_ROLES, "EventCommand | Can't remove role to member!");
                        }
                    }
                }
            }
        }
    }
}
