package main.de.confusingbot.commands.cmds.admincmds.rolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.util.List;

public class RoleCommand implements ServerCommand
{
    public RoleCommand()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "createrole [name] ([#HexColor])\n``` ```Create a new role```");
    }

    private Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        // args0  args1 args2  args3
        //- role create <Name> #color
        //- role delete @role

        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);

        message.delete().queue();
        if (member.hasPermission(channel, Permission.MANAGE_ROLES))
        {
            if (args.length >= 1)
            {
                switch (args[1])
                {
                    case "create":
                        CreateRole(args, channel, guild);
                        break;
                    case "delete":
                        DeleteRole(args, channel, message);
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
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void CreateRole(String[] args, TextChannel channel, Guild guild)
    {
        int length = args.length;
        if (length > 2)
        {
            StringBuilder builder = new StringBuilder();
            String roleName;
            Color roleColor;

            if (args[length - 1].startsWith("#") && args.length > 3)//if the last arg is a HexColor
            {
                for (int i = 2; i < length - 1; i++) builder.append(args[i] + " ");

                roleName = builder.toString().trim();
                String hexCode = args[length - 1];

                roleColor = Color.decode(hexCode);
            }
            else
            {
                for (int i = 2; i < length; i++) builder.append(args[i] + " ");

                roleName = builder.toString().trim();
                roleColor = new Color(255, 255, 255);
            }
            //Create Role
            createRole(guild, roleName, roleColor);
            //Message
            embeds.SuccessfulCreatedRole(channel, roleName);
        }
        else
        {
            //Usage
            embeds.CreateUsage(channel);
        }
    }

    private void DeleteRole(String[] args, TextChannel channel, Message message)
    {
        if (args.length > 2)
        {
            List<Role> roles = message.getMentionedRoles();
            if (!roles.isEmpty())
            {
                Role role = roles.get(0);

                //Delete Role
                role.delete().queue();

                //Message
                embeds.SuccessfulDeletedRole(channel, role.getName());
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
            embeds.DeleteUsage(channel);
        }

    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void createRole(Guild guild, String roleName, Color roleColor)
    {
        RoleAction roleAction = guild.createRole();
        roleAction.setName(roleName);
        roleAction.setColor(roleColor);
        roleAction.setMentionable(true);
        roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
        roleAction.queue();
    }
}
