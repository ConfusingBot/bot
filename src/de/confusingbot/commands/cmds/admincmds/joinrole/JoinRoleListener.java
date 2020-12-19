package de.confusingbot.commands.cmds.admincmds.joinrole;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

public class JoinRoleListener
{

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        List<Long> roleids = JoinRoleManager.sql.getRoleIDs(guildid);

        //Needed Permissions
        Permission MANAGE_ROLES = Permission.MANAGE_ROLES;

        if (!roleids.isEmpty())
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();
            Member bot = guild.getSelfMember();

            if (bot.hasPermission(MANAGE_ROLES))
            {
                for (long roleid : roleids)
                {
                    Role role = guild.getRoleById(roleid);
                    if (role != null)
                    {
                        CommandsUtil.AddOrRemoveRoleFromMember(guild, member, role, true);
                    }
                    else
                    {
                        //Error
                        JoinRoleManager.embeds.RoleDoesNotExistError(event.getGuild().getDefaultChannel(), roleid);

                        //SQL
                        JoinRoleManager.sql.removeFormSQL(guildid, roleid);
                    }
                }
            }
            else
            {
                //Error
                EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_ROLES, "JoinCommand | Can't add role to member!");
            }
        }
    }
}
