package main.de.confusingbot.commands.cmds.ownercmds;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class EmbedsUtil
{
    public static int showUsageTime = 10;
    public static int showErrorTime = 5;
    public static int showSuccessTime = 3;

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    //Permission
    public static void NoPermissionError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Sry I am not allowed to execute this command⚡️", channel, showErrorTime);
    }

    //Number
    public static void NoValidIDNumberError(TextChannel channel, String idString)
    {
        EmbedManager.SendErrorEmbed( "This is no ID " + idString, channel, showErrorTime);
    }
}
