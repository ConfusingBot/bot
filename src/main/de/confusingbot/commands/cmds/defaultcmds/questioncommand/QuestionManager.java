package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import net.dv8tion.jda.api.Permission;

public class QuestionManager
{

    public static Embeds embeds = new Embeds();
    public static SQL sql = new SQL();

    public static String questionKey = "QUESTION:";

    public static Permission questionCategoryPermission = Permission.MANAGE_CHANNEL;
}
