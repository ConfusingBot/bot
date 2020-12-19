package de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import net.dv8tion.jda.api.Permission;

public class RepeatInfoCommandManager
{
    public static SQL sql = new SQL();
    public static Embeds embeds = new Embeds();

    public static String infoKey = "INFO:";
    public static int maxRepeatInfos = 3;
    public static int maxGapInHours = 999;// h

    public static Permission permission = Permission.MESSAGE_MANAGE;
}
