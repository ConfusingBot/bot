package main.de.confusingbot.commands.help;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.entities.Message;

public class CommandsUtil {

    public static String[] messageToArgs(Message message){
        String m = message.getContentDisplay();
        m.replace("   ", " ");
        m.replace("  ", " ");
        String[] args = m.substring(Main.prefix.length()).split(" ");

        return args;
    }

    public static boolean isNumeric(String strNum)
    {
        try
        {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException | NullPointerException nfe)
        {
            return false;
        }
        return true;
    }
}
