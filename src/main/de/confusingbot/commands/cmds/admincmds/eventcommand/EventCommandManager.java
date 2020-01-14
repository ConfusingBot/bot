package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import net.dv8tion.jda.api.Permission;

public class EventCommandManager
{

    public static SQL sql = new SQL();
    public static Embeds embeds = new Embeds();

    public static String messageStartKey = "MESSAGE:";

    public static Permission permission = Permission.MANAGE_SERVER;
}
