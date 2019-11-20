package main.de.confusingbot.commands.cmds.strings;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class StringsUtil
{
    public static int showUsageTime = 10;
    public static int showErrorTime = 5;
    public static int showSuccessTime = 3;
    public static int showInfoTime = 5;

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================

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

    public static void NoNumberError(TextChannel channel, String numberString)
    {
        EmbedManager.SendErrorEmbed( "This is no Number " + numberString, channel, showErrorTime);
    }

    //Exists
    public static void AlreadyExistsError(TextChannel channel, String name){
        EmbedManager.SendErrorEmbed( "This **" + name + "** already exists!", channel, 3);
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

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public static void SuccessfulAdded(TextChannel channel, String name, String toName)
    {
        EmbedManager.SendSuccessEmbed("You successfully added **" + name + "** to " + toName + "!", channel, showSuccessTime);
    }

    public static void SuccessfulRemoved(TextChannel channel, String name, String formName){
        EmbedManager.SendSuccessEmbed("You successfully removed the **" + name + "** from " + formName + "!", channel, showSuccessTime);
    }

    public static void SuccessfulDeleted(TextChannel channel, String name, String formName){
        EmbedManager.SendSuccessEmbed("You successfully deleted the **" + name + "** from " + formName + "!", channel, showSuccessTime);
    }

    public static void SuccessfulCreated(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully created **" + name + "**!", channel, showSuccessTime);
    }


}
