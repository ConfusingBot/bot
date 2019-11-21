package main.de.confusingbot.commands.cmds.musiccmds.queuecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds {
    public Embeds() {
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue\n``` ```Offers you the MusicQueue```");
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue clear\n``` ```Clear the MusicQueue```");
        HelpManager.music.add("```yaml\n" + Main.prefix + "queue delete [index]\n``` ```Delete the Track at index```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel) {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "queue delete [position]`\n`" + Main.prefix + "queue clear`", channel, EmbedsUtil.showUsageTime);
    }

    public void ListQueueUsage(TextChannel channel) {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "queue`", channel, EmbedsUtil.showUsageTime);
    }

    public void ClearQueueUsage(TextChannel channel) {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "queue clear`", channel, EmbedsUtil.showUsageTime);
    }

    public void DeleteAtIndexUsage(TextChannel channel) {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "queue delete [position]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyClearedMusicQueue(TextChannel channel) {
        EmbedManager.SendCustomEmbed("Music Queue Cleared\uD83D\uDDD1", "You cleared the Music Queue", Color.decode("#d400ff"), channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyDeletedTrackAtIndex(TextChannel channel, int index, String title) {
        EmbedManager.SendCustomEmbed("Track deleted at " + index,
                "You deleted *" + title + "* from the queue!", Color.decode("#d400ff"), channel, 5);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void CouldNotDeleteTrackAtIndex(TextChannel channel, int index) {
        EmbedManager.SendErrorEmbed("Couldn't delete Track at " + index, channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void NoExistingMusicQueueEmbed(TextChannel channel) {
        EmbedManager.SendCustomEmbed("Music Queue\uD83D\uDC7E", "No existing MusicQueue\uD83C\uDFB6", Color.decode("#d400ff"), channel, EmbedsUtil.showInfoTime);
    }

    public void SendMusicQueueEmbed(TextChannel channel, String queueString, boolean first) {
        String title = "";
        if (first) {
            title = "Music Queue\uD83D\uDC7E";
        }
        EmbedManager.SendCustomEmbed(title, queueString, Color.decode("#d400ff"), channel, 15);
    }

}
