package main.de.confusingbot.commands.cmds.admincmds.joinrole;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class JoinRoleCommand implements ServerCommand {

    public JoinRoleCommand(){
        JoinRoleManager.embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        //- joinrole add @role
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
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
            JoinRoleManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void addCommand(String[] args, TextChannel channel, Message message)
    {
        long guildID = channel.getGuild().getIdLong();
        if (args.length == 3)
        {
            List<Role> roles = message.getMentionedRoles();
            if(roles.isEmpty()){
                Role role = roles.get(0);
                long roleid = role.getIdLong();
                if (!JoinRoleManager.sql.ExistsInSQL(guildID, roleid))
                {
                    //SQL
                    JoinRoleManager.sql.addToSQL(guildID, roleid);

                    //Message
                    JoinRoleManager.embeds.SuccessfulAddedJoinRole(channel);
                }
                else
                {
                    //Error
                    JoinRoleManager.embeds.AlreadyExistingJoinRoleInformation(channel, role);
                }
            }else{
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
        if (args.length == 3)
        {
            List<Role> roles = message.getMentionedRoles();
            if(roles.isEmpty()){
                Role role = roles.get(0);
                long roleid = role.getIdLong();
                if (JoinRoleManager.sql.ExistsInSQL(guildID, roleid))
                {
                    //SQL
                    JoinRoleManager.sql.removeFormSQL(guildID, roleid);

                    //Message
                    JoinRoleManager.embeds.SuccessfulRemovedJoinRole(channel);
                }
                else
                {
                    //Error
                    JoinRoleManager.embeds.NoExistingJoinRoleInformation(channel, role);
                }
            }else{
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
     if (args.length == 2){

     }else{
         JoinRoleManager.embeds.ListUsage(channel);
     }
    }

}
