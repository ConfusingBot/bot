package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import net.dv8tion.jda.api.Permission;

import java.time.format.DateTimeFormatter;

public class QuestionManager
{

    public static Embeds embeds = new Embeds();
    public static SQL sql = new SQL();

    public static String questionKey = "QUESTION:";
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static int deleteMessageAfterXHours = 24;
    public static int addHoursAfterActivity = 5;
    public static boolean hours = false;//TODO to hours

    public static Permission questionCategoryPermission = Permission.MANAGE_CHANNEL;
}
