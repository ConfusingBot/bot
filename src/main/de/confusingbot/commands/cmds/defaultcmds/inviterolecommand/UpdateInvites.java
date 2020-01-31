package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.joinrole.JoinRoleManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInvites
{

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM inviterole");
            while (set.next())
            {
                int inviteCount = set.getInt("invitions");
                long roleID = set.getLong("roleid");
                long guildID = set.getLong("guildid");

                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);

                guild.retrieveInvites().queue(invites -> {

                    for (Invite invite : invites)
                    {
                        if (invite.getMaxAge() != 0) continue;

                        if (invite.getUses() >= inviteCount)
                        {
                            Role role = guild.getRoleById(roleID);
                            if (role != null)
                            {
                                Member member = guild.getMemberById(invite.getInviter().getId());
                                if (member != null)
                                {
                                    try
                                    {
                                        //Add role
                                        guild.addRoleToMember(member, guild.getRoleById(roleID)).queue();
                                    } catch (HierarchyException e)
                                    {
                                        //Error
                                        InviteRoleManager.embeds.BotHasNoPermissionToAssignRole(guild.getDefaultChannel(), role);
                                    }
                                }
                            }
                            else
                            {
                                //Error
                                InviteRoleManager.embeds.RoleDoesNotExistError(guild.getDefaultChannel(), roleID);

                                //SQL
                                InviteRoleManager.sql.RemoveRoleFromSQL(guildID, roleID);
                            }
                        }
                    }
                });
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
