package main.de.confusingbot.commands.cmds.musiccmds;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class EmbedsUtil
{
    public static int showUsageTime = 10;
    public static int showErrorTime = 5;
    public static int showSuccessTime = 3;
    public static int showInfoTime = 5;

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    //Permission
    public static void NoPermissionError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Sry I am not allowed to execute this command⚡️", channel, showErrorTime);
    }

    public static void BotNotInVoiceChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("The bot is in no VoiceChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public static void BotNotInYourVoiceChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("The bot is in not in your VoiceChannel!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public static void YouAreNotInAVoiceChannelInformation(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`Ups, you aren't in a VoiceChannel\uD83C\uDF99`", channel, EmbedsUtil.showInfoTime);
    }

}
