package de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import de.confusingbot.commands.cmds.admincmds.rolebordercommand.SQL;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.List;

public class AcceptRulesListener
{
    de.confusingbot.commands.cmds.admincmds.rolebordercommand.SQL roleBorderSQL = new SQL();

    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;
    Permission MESSAGE_MANAGE = Permission.MESSAGE_MANAGE;

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();

        Member member = event.getMember();
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (AcceptRuleManager.sql.containsMessageID(guild.getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    if (bot.hasPermission(event.getTextChannel(), MANAGE_ROLES))
                    {
                        long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                        long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                        //Add roleAccepted to Member
                        Role roleAccepted = guild.getRoleById(roleacceptedid);
                        if (roleAccepted != null)
                        {
                            CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleAccepted, true);
                        }
                        else
                        {
                            //Error
                            AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), roleacceptedid);
                        }

                        //Remove NotAccepted from Member
                        Role roleNotAccepted = rolenotacceptedid != -1 ? guild.getRoleById(rolenotacceptedid) : null;
                        if (roleNotAccepted != null)
                        {
                            CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleNotAccepted, false);
                        }
                        else
                        {
                            //Error
                            if (rolenotacceptedid != -1)
                                AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), rolenotacceptedid);
                        }


                        //add roleborder
                        List<Long> roleBorderIDs = AcceptRuleManager.sql.getRoleBorderIDs(guild);
                        if (roleBorderIDs != null)
                        {
                            for (int i = 0; i < roleBorderIDs.size(); i++)
                            {
                                Role roleBorder = guild.getRoleById(roleBorderIDs.get(i));
                                if (roleBorder != null)
                                {
                                    CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleBorder, true);
                                }
                                else
                                {
                                    //Error
                                    AcceptRuleManager.embeds.RoleBorderDoesNotExistAnymore(event.getTextChannel(), roleBorderIDs.get(i));

                                    //SQL
                                    roleBorderSQL.removeFromSQL(guild.getIdLong(), roleBorderIDs.get(i));
                                }
                            }
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(event.getTextChannel(), MANAGE_ROLES, "AcceptRuleCommand | Can't add role to member!");
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

                if (AcceptRuleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
                {
                    if (!event.getUser().isBot())
                    {
                        if (bot.hasPermission(event.getTextChannel(), MANAGE_ROLES))
                        {
                            long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                            long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                            //Remove roleAccepted from Member
                            Role roleAccepted = guild.getRoleById(roleacceptedid);
                            if (roleAccepted != null)
                            {
                                CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleAccepted, false);
                            }
                            else
                            {
                                //Error
                                AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), roleacceptedid);
                            }

                            //Add roleNotAccepted to Member
                            Role roleNotAccepted = rolenotacceptedid != -1 ? guild.getRoleById(rolenotacceptedid) : null;
                            if (roleNotAccepted != null)
                            {
                                CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleNotAccepted, true);
                            }
                            else
                            {
                                //Error
                                if (rolenotacceptedid != -1)
                                    AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), rolenotacceptedid);
                            }

                            //remove roleborders
                            List<Long> roleBorderIDs = AcceptRuleManager.sql.getRoleBorderIDs(guild);
                            if (roleBorderIDs != null)
                            {
                                for (int i = 0; i < roleBorderIDs.size(); i++)
                                {
                                    Role roleBorder = guild.getRoleById(roleBorderIDs.get(i));
                                    if (roleBorder != null)
                                    {
                                        CommandsUtil.AddOrRemoveRoleFromMember(guild, member, roleBorder, false);
                                    }
                                    else
                                    {
                                        //Error
                                        AcceptRuleManager.embeds.RoleBorderDoesNotExistAnymore(event.getTextChannel(), roleBorderIDs.get(i));

                                        //SQL
                                        roleBorderSQL.removeFromSQL(guild.getIdLong(), roleBorderIDs.get(i));
                                    }
                                }
                            }
                        }
                        else
                        {
                            //Error
                            EmbedManager.SendNoPermissionEmbed(event.getTextChannel(), MANAGE_ROLES, "AcceptRuleCommand | Can't remove role from member!");
                        }
                    }
                }
            }
        }
    }

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();

        long guildid = guild.getIdLong();
        long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(guildid);


        if (rolenotacceptedid != -1)
        {
            if (bot.hasPermission(MANAGE_ROLES))
            {
                Member member = event.getMember();

                Role notAcceptedRole = rolenotacceptedid != -1 ? guild.getRoleById(rolenotacceptedid) : null;

                if (notAcceptedRole != null)
                {
                    //Add Role to member
                    CommandsUtil.AddOrRemoveRoleFromMember(guild, member, notAcceptedRole, true);
                }
                else
                {
                    AcceptRuleManager.embeds.RoleDoesNotExistAnymore(guild.getDefaultChannel(), rolenotacceptedid);
                }
            }
            else
            {
                //Error
                EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_ROLES, "AcceptRuleCommand | Can't not add role to member!");
            }
        }
    }

    public void onMemberLeaveListener(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();
        Member bot = guild.getSelfMember();
        long messageID = AcceptRuleManager.sql.getMessageID(guild.getIdLong());
        long channelID = AcceptRuleManager.sql.getChannelID(guild.getIdLong());


        if (messageID != -1 && channelID != -1)
        {
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
                        if (bot.hasPermission(MESSAGE_MANAGE))
                        {
                            try
                            {
                                message.removeReaction(reaction.getReactionEmote().getEmote(), user).queue();
                            } catch (IllegalStateException e)
                            {
                                message.removeReaction(reaction.getReactionEmote().getEmoji(), user).queue();
                            }
                        }
                        else
                        {
                            //Error
                            EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MESSAGE_MANAGE, "AcceptRuleCommand | Can't remove reaction from leave Member!");
                        }
                    }
                }
            }
        }
    }
}
