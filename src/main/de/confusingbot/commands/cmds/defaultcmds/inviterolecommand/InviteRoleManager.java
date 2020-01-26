package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import net.dv8tion.jda.api.Permission;

public class InviteRoleManager
{
    public static Embeds embeds = new Embeds();
    public static SQL sql = new SQL();

    public static Permission createInviteRolePermission = Permission.MANAGE_ROLES;
}
