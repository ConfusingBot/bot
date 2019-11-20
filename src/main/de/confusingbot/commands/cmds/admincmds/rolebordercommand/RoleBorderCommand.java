package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.List;

public class RoleBorderCommand implements ServerCommand
{
    private SQL sql = new SQL();
    private Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        // - roleborder add [@role]
        // - roleborder remove [@role]
        // - roleborder create [name] #2f3136

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {

            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "add":
                        AddCommand(args, message, channel);
                        break;
                    case "create":
                        CreateCommand(args, message.getGuild(), channel);
                        break;
                    case "remove":
                        RemoveCommand(args, message, channel);
                        break;
                    default:
                        //Usage
                        embeds.GeneralUsage(channel);
                        break;
                }
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void AddCommand(String[] args, Message message, TextChannel channel)
    {
        if (args.length >= 3)
        {
            List<Role> roles = message.getMentionedRoles();
            if (!roles.isEmpty())
            {
                Role role = roles.get(0);

                if (!sql.ExistsInSQL(channel.getGuild().getIdLong(), role.getIdLong()))
                {
                    //SQL
                    sql.addToSQL(channel.getIdLong(), role.getIdLong(), role.getName());

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

    private void CreateCommand(String[] args, Guild guild, TextChannel channel)
    {
        if (args.length >= 3)
        {
            //get role name
            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < args.length; i++) builder.append(args[i] + " ");

            String roleName = "\u2063     " + builder.toString().trim() + "     \u2063";

            //create role
            RoleAction roleAction = guild.createRole();
            roleAction.setName(roleName);
            roleAction.setColor(Color.decode("#2f3136"));
            roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
            long roleid = roleAction.complete().getIdLong();

            //SQLite
            sql.addToSQL(channel.getIdLong(), roleid, roleName);

            //Message
            embeds.SuccessfullyCreateRoleBorder(channel, roleName);
        }
        else
        {
            //Usage
            embeds.CreateUsage(channel);
        }
    }

    private void RemoveCommand(String[] args, Message message, TextChannel channel)
    {
        if (args.length >= 3)
        {
            List<Role> roles = message.getMentionedRoles();
            if (!roles.isEmpty())
            {
                Role role = roles.get(0);

                if (sql.ExistsInSQL(channel.getGuild().getIdLong(), role.getIdLong()))
                {
                    //SQLite
                    sql.removeFromSQL(channel.getGuild().getIdLong(), role.getIdLong());

                    //delete Role
                    role.delete().queue();

                    //Message
                    embeds.SuccessfullyRemovedRoleBorder(channel, role.getName());
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

}
