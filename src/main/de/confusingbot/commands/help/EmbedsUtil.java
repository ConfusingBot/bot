package main.de.confusingbot.commands.help;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

public class EmbedsUtil
{
    public static int showUsageTime = 120;
    public static int showErrorTime = 10;
    public static int showInfoTime = 15;
    public static int showSuccessTime = 5;

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public static void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedManager.SendErrorEmbed("`Sry I am not allowed to execute this command⚡️`\n ```yaml\n" + permission.name() + "```\n", channel, showErrorTime);
    }

    public static void NoValidIDNumberError(TextChannel channel, String idString)
    {
        EmbedManager.SendErrorEmbed( "This is no ID " + idString, channel, showErrorTime);
    }

    public static void NoNumberError(TextChannel channel, String numberString)
    {
        EmbedManager.SendErrorEmbed( "This is no valid Number **" + numberString + "**", channel, showErrorTime);
    }

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
        EmbedManager.SendErrorEmbed( "You haven't mentioned a **" + name + "**", channel, showErrorTime);
    }

    public static void NoPermissionError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Sry I am not allowed to execute this command⚡️", channel, showErrorTime);
    }

    public static void BotNotInVoiceChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Ups, the bot is in no VoiceChannel..\nUse `/play` for playing music", channel, showErrorTime);
    }

    public static void BotNotInYourVoiceChannelError(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("Sry.. but you can't control the ConfusingBot in another voice channel!", channel, showErrorTime);
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

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public static void YouAreNotInAVoiceChannelInformation(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`Ups, you aren't in a VoiceChannel\uD83C\uDF99`\nFill it with `/play`", channel, EmbedsUtil.showInfoTime);
    }

    public static void NoSpaceForBotInformation(TextChannel channel){
        EmbedManager.SendInfoEmbed( "In your channel is no space for me \uD83D\uDE25", channel, showInfoTime);
    }

}
