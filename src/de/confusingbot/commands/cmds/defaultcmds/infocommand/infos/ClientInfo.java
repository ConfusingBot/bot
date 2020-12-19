package de.confusingbot.commands.cmds.defaultcmds.infocommand.infos;

import de.confusingbot.commands.cmds.admincmds.rolebordercommand.SQL;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommandManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;
import java.util.stream.Collectors;

public class ClientInfo
{
    public String displayActivityInfo(Member member)
    {
        try
        {
            String activity = member.getActivities().get(0).getName();

            return activity;
        } catch (Exception e)
        {
            return InfoCommandManager.noInformationString;
        }
    }

    public String displayJoinDate(Member member)
    {
        try
        {
            String joinedDate = InfoCommandManager.formatter.format(member.getTimeJoined().toLocalDate());
            return joinedDate;
        } catch (Exception e)
        {
            return InfoCommandManager.noInformationString;
        }
    }

    public String displayCreateDate(Member member)
    {
        try
        {
            String createdDate = InfoCommandManager.formatter.format(member.getTimeCreated().toLocalDate());
            return createdDate;
        } catch (Exception e)
        {
            return InfoCommandManager.noInformationString;
        }
    }

    public String getRolesFromMemberAsString(Member member)
    {
        StringBuilder roleBuilder = new StringBuilder();
        Guild guild = member.getGuild();

        List<Role> roles = member.getRoles().stream().collect(Collectors.toList());
        List<Long> roleBorderIDs = InfoCommandManager.sql.getRoleBorders(guild.getIdLong());

        for (Role role : roles)
        {
            boolean borderRole = false;
            for (Long roleBorderID : roleBorderIDs)
            {
                Role roleBorder = guild.getRoleById(roleBorderID);
                if (roleBorder != null)
                {
                    if (roleBorder.getIdLong() == role.getIdLong())
                    {
                        roleBuilder.append("\n**" + role.getName() + "**\n");
                        borderRole = true;
                    }
                }
                else
                {
                    //Remove RoleBorder from SQL
                    new SQL().removeFromSQL(guild.getIdLong(), roleBorderID);
                }
            }
            if (!borderRole)
                roleBuilder.append("" + role.getAsMention() + "  ");
        }
        return roleBuilder.toString();
    }
}
