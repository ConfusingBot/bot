package main.de.confusingbot.commands.cmds.musiccmds.leavecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public Embeds()
    {
        HelpManager. music.add("```yaml\n" + Main.prefix + "leave\n``` ```ConfusingBot will leave your channel```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void LeaveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "`leave`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void StoppedMusicMessage(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Stopped Music\uD83C\uDFB6", "", Color.decode("#d400ff"), channel, 5);
    }

}
