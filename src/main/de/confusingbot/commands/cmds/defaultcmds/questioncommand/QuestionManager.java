package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.Permission;

import java.time.format.DateTimeFormatter;

public class QuestionManager
{

    public static Embeds embeds = new Embeds();
    public static SQL sql = new SQL();

    public static String questionKey = "QUESTION:";
    public static DateTimeFormatter formatter = CommandsUtil.formatter;
    public static String DefaultQuestionName = "❓question";
    public static int deleteMessageAfterXHours = 24;
    public static int extraHoursAfterMessage = 5; // h
    public static int startUpdatingDeleteTime = 5; // h
    public static boolean hours = true;

    public static Permission questionCategoryPermission = Permission.MANAGE_CHANNEL;
}
