package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.joinrole.JoinRoleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateInvites
{

    //Needed Permissions
    Permission MANAGE_SERVER = Permission.MANAGE_SERVER;
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;

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
                Member bot = guild.getSelfMember();

                if (bot.hasPermission(MANAGE_SERVER))
                {
                    if (bot.hasPermission(MANAGE_ROLES))
                    {
                        guild.retrieveInvites().queue(invites -> {

                            for (Invite invite : invites)
                            {
                                if (invite.getMaxAge() != 0) continue;//Check if invite is a perma invite!

                                if (invite.getUses() >= inviteCount)
                                {
                                    Role role = guild.getRoleById(roleID);
                                    if (role != null)
                                    {
                                        Member member = guild.getMemberById(invite.getInviter().getId());
                                        if (member != null)
                                        {
                                            //Add role
                                            CommandsUtil.AddOrRemoveRoleFromMember(guild, member, role, true);
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
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_ROLES, "InviteCommand | Can't add role to member!");
                    }
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_SERVER, "InviteCommand | Can't read server invites!");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
