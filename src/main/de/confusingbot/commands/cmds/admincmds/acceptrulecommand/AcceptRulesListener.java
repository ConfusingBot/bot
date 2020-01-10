package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.util.List;

public class AcceptRulesListener
{

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        Member member = event.getMember();
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (AcceptRuleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();
                    long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                    long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                    //Add roleAccepted to Member
                    Role roleAccepted = guild.getRoleById(roleacceptedid);
                    if (roleAccepted != null)
                    {
                        try
                        {
                            guild.addRoleToMember(event.getMember(), roleAccepted).queue();
                        } catch (HierarchyException e)
                        {
                            //Error
                            AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleAccepted);
                        }
                    }
                    else
                    {
                        //Error
                        AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), roleacceptedid);
                    }

                    //Remove NotAccepted from Member
                    Role roleNotAccepted = guild.getRoleById(rolenotacceptedid);
                    if (roleNotAccepted != null)
                    {
                        try
                        {
                            guild.removeRoleFromMember(event.getMember(), roleNotAccepted).queue();
                        } catch (HierarchyException e)
                        {
                            //Error
                            AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleNotAccepted);
                        }
                    }
                    else
                    {
                        //Error
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
                                try
                                {
                                    guild.addRoleToMember(member, roleBorder).queue();
                                } catch (HierarchyException e)
                                {
                                    //Error
                                    AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleBorder);
                                }
                            }
                            else
                            {
                                //Error
                                AcceptRuleManager.embeds.RoleBorderDoesNotExistAnymore(event.getTextChannel(), roleBorderIDs.get(i));
                            }
                        }
                    }
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
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
                        Guild guild = event.getGuild();

                        long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                        long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                        //Remove roleAccepted from Member
                        Role roleAccepted = guild.getRoleById(roleacceptedid);
                        if (roleAccepted != null)
                        {
                            try
                            {
                                guild.removeRoleFromMember(event.getMember(), roleAccepted).queue();
                            } catch (HierarchyException e)
                            {
                                //Error
                                AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleAccepted);
                            }
                        }
                        else
                        {
                            //Error
                            AcceptRuleManager.embeds.RoleDoesNotExistAnymore(event.getTextChannel(), roleacceptedid);
                        }

                        //Add roleNotAccepted to Member
                        Role roleNotAccepted = guild.getRoleById(rolenotacceptedid);
                        if (roleNotAccepted != null)
                        {
                            try
                            {
                                guild.addRoleToMember(event.getMember(), roleNotAccepted).queue();
                            } catch (HierarchyException e)
                            {
                                //Error
                                AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleNotAccepted);
                            }
                        }
                        else
                        {
                            //Error
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
                                    try
                                    {
                                        guild.removeRoleFromMember(member, roleBorder).queue();
                                    } catch (HierarchyException e)
                                    {
                                        //Error
                                        AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(event.getTextChannel(), roleBorder);
                                    }
                                }
                                else
                                {
                                    //Error
                                    AcceptRuleManager.embeds.RoleBorderDoesNotExistAnymore(event.getTextChannel(), roleBorderIDs.get(i));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(guildid);

        if (rolenotacceptedid != -1)
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();

            Role notAcceptedRole = guild.getRoleById(rolenotacceptedid);

            if (notAcceptedRole != null)
            {
                try
                {
                    //Add Role to member
                    guild.addRoleToMember(member, notAcceptedRole).queue();
                } catch (HierarchyException e)
                {
                    //Error
                    AcceptRuleManager.embeds.BotHasNoPermissionToAssignRole(guild.getDefaultChannel(), notAcceptedRole);
                }
            }
            else
            {
                AcceptRuleManager.embeds.RoleDoesNotExistAnymore(guild.getDefaultChannel(), rolenotacceptedid);
            }
        }
    }

    public void onMemberLeaveListener(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();
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
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void addMemberRole(Guild guild, Member member, long roleid)
    {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }
}
