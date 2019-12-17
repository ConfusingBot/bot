package main.de.confusingbot.commands.cmds.admincmds.joinrole;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;

import java.util.List;

public class JoinRoleListener {

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        List<Long> roleids = JoinRoleManager.sql.getRoleIDs(guildid);

        if (!roleids.isEmpty())
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();

            for(long roleid : roleids){
                guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
            }
        }
    }
}
