package main.de.confusingbot.commands.cmds.musiccmds.shufflecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public Embeds()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "shuffle\n``` ```Shuffle your queue```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ShuffleUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "shuffle`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyShuffledQueue(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Shuffled", "Shuffled playlist\uD83D\uDD79", Color.decode("#d400ff"), channel, EmbedsUtil.showSuccessTime);
    }
}
