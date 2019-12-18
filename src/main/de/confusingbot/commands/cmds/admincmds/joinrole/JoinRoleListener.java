package main.de.confusingbot.commands.cmds.admincmds.joinrole;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.util.List;

public class JoinRoleListener {

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {

            long guildid = event.getGuild().getIdLong();
            List<Long> roleids = JoinRoleManager.sql.getRoleIDs(guildid);

            if (!roleids.isEmpty()) {
                Member member = event.getMember();
                Guild guild = event.getGuild();

                for (long roleid : roleids) {
                    Role role = guild.getRoleById(roleid);
                    if(role != null){
                        try{
                            guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
                        }catch (HierarchyException e){
                            //Error
                            JoinRoleManager.embeds.BotHasNoPermissionToAssignRole(event.getGuild().getDefaultChannel(), role);
                        }
                    }else{
                        //Error
                        JoinRoleManager.embeds.RoleDoesNotExistError(event.getGuild().getDefaultChannel(), roleid);

                        //SQL
                        JoinRoleManager.sql.removeFormSQL(guildid, roleid);
                    }

                }
            }



    }
}
