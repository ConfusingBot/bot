package main.de.confusingbot.commands.cmds.defaultcmds;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
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
    public static void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedManager.SendErrorEmbed("`Sry I am not allowed to execute this command⚡️`\n ```yaml\n" + permission.name() + "```\n", channel, showErrorTime);
    }

    //Number
    public static void NoValidIDNumberError(TextChannel channel, String idString)
    {
        EmbedManager.SendErrorEmbed( "This is no ID " + idString, channel, showErrorTime);
    }

    public static void NotExistingError(TextChannel channel, String name){
        EmbedManager.SendErrorEmbed("**" + name + "** doesn't exists", channel, showErrorTime);
    }

    public static void OnlyOneAllowedToExistError(TextChannel channel, String name){
        EmbedManager.SendErrorEmbed("You can only create on **" + name + "**!", channel, showErrorTime);
    }

    public static void HavenNotMentionedError(TextChannel channel, String name){
        EmbedManager.SendErrorEmbed( "You haven't mentioned a " + name, channel, showErrorTime);
    }

    public static void NoNumberError(TextChannel channel, String numberString)
    {
        EmbedManager.SendErrorEmbed( "This is no Number " + numberString, channel, showErrorTime);
    }

}
