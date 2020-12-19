package de.confusingbot.commands.cmds.musiccmds.queuecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue\n``` ```Offers you the MusicQueue```");
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue clear\n``` ```Clear the MusicQueue```");
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue delete [index]\n``` ```Delete the Track at [index]```");
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue shuffle\n``` ```Shuffles the queue```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "queue delete [index]\n```"
                        + "```Delete the Track at [index]```"
                        + "```yaml\n" + Main.prefix + "queue clear\n```"
                        + " ```Clears the whole queue```"
                        + "```yaml\n" + Main.prefix + "queue shuffle\n```"
                        + " ```Shuffles the queue```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void ListQueueUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "queue`", channel, EmbedsUtil.showUsageTime);
    }

    public void ShuffleUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "queue shuffle\n```"
                + " ```Shuffles the queue```", channel, EmbedsUtil.showUsageTime);
    }

    public void ClearQueueUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "queue clear\n```"
                + " ```Clears the whole queue```", channel, EmbedsUtil.showUsageTime);
    }

    public void DeleteAtIndexUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "queue delete [index]\n```"
                + "```Delete the Track at [index]```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyClearedMusicQueue(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Music Queue Cleared\uD83D\uDDD1", "You cleared the Music Queue", Color.decode("#d400ff"), channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyDeletedTrackAtIndex(TextChannel channel, int index, String title)
    {
        EmbedManager.SendCustomEmbed("Track deleted at " + index,
                "You deleted **" + title + "** from the queue!", Color.decode("#d400ff"), channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyShuffledQueue(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Shuffled", "Shuffled playlist\uD83D\uDD79", Color.decode("#d400ff"), channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void CouldNotDeleteTrackAtIndex(TextChannel channel, int index)
    {
        EmbedManager.SendErrorEmbed("Couldn't delete Track at " + index, channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void NoSongInQueueInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("Sry.. but I can't shuffle a empty playlist\uD83E\uDD14", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void NoExistingMusicQueueEmbed(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Music Queue\uD83D\uDC7E", "No existing MusicQueue\uD83C\uDFB6", Color.decode("#d400ff"), channel, EmbedsUtil.showInfoTime);
    }

    public void SendMusicQueueEmbed(TextChannel channel, String queueString, boolean first, boolean last, boolean queueToLarge, int trackOver, String lastMemberName)
    {
        String title = " ";
        if (first)
        {
            title = "Music Queue\uD83D\uDC7E from " + lastMemberName;
        }
        if (queueToLarge && last)
        {
            queueString += "\n ..." + trackOver + " other songs!";
        }

        EmbedManager.SendCustomEmbed(title, queueString, Color.decode("#d400ff"), channel, 30);
    }

}
