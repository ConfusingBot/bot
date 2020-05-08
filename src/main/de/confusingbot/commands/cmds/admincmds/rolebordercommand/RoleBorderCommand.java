package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.List;

public class RoleBorderCommand implements ServerCommand
{
    private SQL sql = RoleBorderCommandManager.sql;
    private Embeds embeds = RoleBorderCommandManager.embeds;

    public RoleBorderCommand()
    {
        embeds.HelpEmbed();
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

        // - roleborder add [@role]
        // - roleborder remove [@role]
        // - roleborder create [name] #2f3136

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(channel, RoleBorderCommandManager.permission))
            {
                if (args.length >= 2)
                {
                    switch (args[1])
                    {
                        case "add":
                            AddCommand(args, message, channel);
                            break;
                        case "create":
                            CreateCommand(args, channel);
                            break;
                        case "remove":
                            RemoveCommand(args, message, channel);
                            break;
                        case "list":
                            ListCommand(channel.getGuild(), channel);
                            break;
                        default:
                            //Usage
                            embeds.GeneralUsage(channel);
                            break;
                    }
                }
                else
                {
                    //Usage
                    embeds.GeneralUsage(channel);
                }
            }
            else
            {
                //Error
                embeds.NoPermissionError(channel, RoleBorderCommandManager.permission);
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        List<Long> roleborderIds = sql.getRoleBordersFromSQL(guild.getIdLong());
        if (roleborderIds.size() != 0 && roleborderIds != null)
        {
            //Create Description -> all voice channel
            String description = "";
            for (long roleborderID : roleborderIds)
            {
                Role roleborder = guild.getRoleById(roleborderID);
                if (roleborder != null)
                {
                    description += "\uD83D\uDCCC  ️" + roleborder.getName() + "\n";
                }
                else
                {
                    description += "\uD83D\uDCCC **RoleBorder does not exists!**\n";
                    //SQLite
                    sql.removeFromSQL(guild.getIdLong(), roleborderID);
                }
            }

            //Message
            embeds.SendRoleBorderListEmbed(channel, description);
        }
        else
        {
            embeds.HasNoRoleBordersInformation(channel);
        }
    }

    private void AddCommand(String[] args, Message message, TextChannel channel)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            Guild guild = channel.getGuild();
            if (args.length >= 3)
            {
                List<Role> roles = message.getMentionedRoles();
                if (!roles.isEmpty())
                {
                    Role role = roles.get(0);

                    if (!sql.ExistsInSQL(guild.getIdLong(), role.getIdLong()))
                    {
                        //Add border to every Member
                        CommandsUtil.AddOrRemoveRoleFromAllMembers(guild, role.getIdLong(), true);

                        //SQL
                        sql.addToSQL(guild.getIdLong(), role.getIdLong(), role.getName());

                        //Message
                        embeds.SuccessfullyAddedRoleBorder(channel, role.getName());
                    }
                    else
                    {
                        //Error
                        embeds.RoleBorderAlreadyExistsError(channel);
                    }
                }
                else
                {
                    //Error
                    embeds.HaveNotMentionedRoleError(channel);
                }
            }
            else
            {
                //Usage
                embeds.AddUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    private void CreateCommand(String[] args, TextChannel channel)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            Guild guild = channel.getGuild();
            if (args.length >= 3)
            {
                //get role name
                StringBuilder builder = new StringBuilder();
                for (int i = 2; i < args.length; i++) builder.append(args[i] + " ");
                String name = builder.toString().trim();
                int maxRoleCharLength = 30;

                if (name.length() <= maxRoleCharLength)
                {
                    String space = createSpace(name, maxRoleCharLength);

                    String roleName = "\u2063" + space + "" + name + "" + space + "\u2063";

                    //create role
                    RoleAction roleAction = guild.createRole();
                    roleAction.setName(roleName);
                    roleAction.setColor(Color.decode("#2f3136"));
                    roleAction.setMentionable(true);
                    roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
                    long roleid = roleAction.complete().getIdLong();

                    //Add border to every Member
                    CommandsUtil.AddOrRemoveRoleFromAllMembers(guild, roleid, true);

                    //SQLite
                    sql.addToSQL(guild.getIdLong(), roleid, name);

                    //Message
                    embeds.SuccessfullyCreateRoleBorder(channel, name);
                }
                else
                {
                    embeds.RoleBorderNameIsToLongError(channel, name);
                }

            }
            else
            {
                //Usage
                embeds.CreateUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    private void RemoveCommand(String[] args, Message message, TextChannel channel)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            Guild guild = channel.getGuild();
            if (args.length >= 3)
            {
                List<Role> roles = message.getMentionedRoles();
                if (!roles.isEmpty())
                {
                    Role role = roles.get(0);
                    long roleid = role.getIdLong();

                    if (sql.ExistsInSQL(guild.getIdLong(), roleid))
                    {
                        //SQLite
                        sql.removeFromSQL(guild.getIdLong(), role.getIdLong());

                        //delete Role
                        role.delete().queue();

                        String roleName = role.getName().trim();
                        //Message
                        embeds.SuccessfullyRemovedRoleBorder(channel, roleName);
                    }
                    else
                    {
                        //Error
                        embeds.RoleDoesNotExistError(channel);
                    }
                }
                else
                {
                    //Error
                    embeds.HaveNotMentionedRoleError(channel);
                }
            }
            else
            {
                //Usage
                embeds.RemoveUsage(channel);
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private String createSpace(String roleName, int maxLength)
    {
        //30 chars is the max length of a roleshown on the side U+2002
        String space = "";
        int nameLength = roleName.length();
        int spaceLength = (maxLength - nameLength);
        if (nameLength % 2 != 0) spaceLength--;

        for (int i = 1; i <= spaceLength / 2; i++) space += "\u2002";

        return space;
    }

}
