package de.confusingbot.commands.cmds.admincmds.joinrole;

import de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleManager;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class JoinRoleCommand implements ServerCommand
{

    public JoinRoleCommand()
    {
        JoinRoleManager.embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- joinrole add @role
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(channel, JoinRoleManager.permission))
            {
                if (args.length >= 2)
                {
                    switch (args[1])
                    {
                        case "add":
                            addCommand(args, channel, message);
                            break;
                        case "remove":
                            removeCommand(args, channel, message);
                            break;
                        case "list":
                            listCommand(args, channel, message);
                            break;
                        default:
                            //Usage
                            AcceptRuleManager.embeds.GeneralUsage(channel);
                            break;
                    }
                }
                else
                {
                    //Usage
                    JoinRoleManager.embeds.GeneralUsage(channel);
                }
            }
            else
            {
                //Error
                JoinRoleManager.embeds.NoPermissionError(channel, JoinRoleManager.permission);
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void addCommand(String[] args, TextChannel channel, Message message)
    {
        long guildID = channel.getGuild().getIdLong();

        if (args.length >= 2)
        {
            List<Role> roles = message.getMentionedRoles();
            if (!roles.isEmpty())
            {
                if (bot.hasPermission(channel, MANAGE_ROLES))
                {
                    Role role = roles.get(0);
                    long roleid = role.getIdLong();
                    if (!JoinRoleManager.sql.ExistsInSQL(guildID, roleid))
                    {
                        //SQL
                        JoinRoleManager.sql.addToSQL(guildID, roleid);

                        //Message
                        JoinRoleManager.embeds.SuccessfulAddedJoinRole(channel, role);
                    }
                    else
                    {
                        //Error
                        JoinRoleManager.embeds.AlreadyExistingJoinRoleError(channel, role);
                    }
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
                }
            }
            else
            {
                //Error
                JoinRoleManager.embeds.NoMentionedRoleError(channel);
            }
        }
        else
        {
            //Usage
            JoinRoleManager.embeds.AddUsage(channel);
        }
    }

    private void removeCommand(String[] args, TextChannel channel, Message message)
    {
        long guildID = channel.getGuild().getIdLong();

        if (args.length >= 2)
        {
            List<Role> roles = message.getMentionedRoles();
            if (!roles.isEmpty())
            {
                if (bot.hasPermission(channel, MANAGE_ROLES))
                {
                    Role role = roles.get(0);
                    long roleid = role.getIdLong();
                    if (JoinRoleManager.sql.ExistsInSQL(guildID, roleid))
                    {
                        //SQL
                        JoinRoleManager.sql.removeFormSQL(guildID, roleid);

                        //Message
                        JoinRoleManager.embeds.SuccessfulRemovedJoinRole(channel, role);
                    }
                    else
                    {
                        //Error
                        JoinRoleManager.embeds.NoExistingJoinRoleError(channel, role);
                    }
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
                }
            }
            else
            {
                //Error
                JoinRoleManager.embeds.NoMentionedRoleError(channel);
            }
        }
        else
        {
            //Usage
            JoinRoleManager.embeds.RemoveUsage(channel);
        }
    }

    private void listCommand(String[] args, TextChannel channel, Message message)
    {
        Guild guild = channel.getGuild();
        if (args.length == 2)
        {
            //SQL
            List<Long> roleids = JoinRoleManager.sql.getRoleIDs(guild.getIdLong());

            if (!roleids.isEmpty())
            {
                //Create Description -> all voice channel
                String description = "";
                for (long roleid : roleids)
                {
                    Role role = guild.getRoleById(roleid);

                    if (role != null)
                    {
                        description += "⚫️" + role.getAsMention() + "\n\n";
                    }
                    else
                    {
                        description += "⚠️**Role does not exist**\n";

                        //SQL
                        JoinRoleManager.sql.removeFormSQL(guild.getIdLong(), roleid);
                    }
                }

                //Message
                JoinRoleManager.embeds.SendJoinRoleList(channel, description);
            }
            else
            {
                JoinRoleManager.embeds.HasNoJoinRoleInformation(channel);
            }
        }
        else
        {
            JoinRoleManager.embeds.ListUsage(channel);
        }
    }

}
