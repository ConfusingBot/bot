package de.confusingbot.commands.cmds.admincmds.messagecommand;

import net.dv8tion.jda.api.Permission;

public class MessageManager
{

    public static SQL sql = new SQL();
    public static Embeds embeds = new Embeds();
    public static String messageStartKey = "MESSAGE:";
    public static String NewMemberKeyWord = "@newMember";
    public static String LeaveMemberKeyWord = "@leaveMember";

    public static String welcomeMessageKey = "welcome";
    public static String leaveMessageKey = "leave";

    public static Permission permission = Permission.MANAGE_SERVER;

}
