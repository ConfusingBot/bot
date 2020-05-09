package main.de.confusingbot.commands.cmds.musiccmds.skipcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "skip\n``` ```Skip the current playing Song```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void SkipUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "skip\n``` ```Skip the current playing Song```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void NoOtherSongInQueueInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("There is no other song in the queue!", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullySkippedTrack(TextChannel channel, String title)
    {
        EmbedManager.SendCustomEmbed("Skipped Song\uD83D\uDD0A", title, Color.decode("#d400ff"), channel, 10);
    }
}
