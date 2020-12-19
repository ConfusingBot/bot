package de.confusingbot.commands.cmds.musiccmds.leavecommand;

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
        HelpManager. music.add("```yaml\n" + Main.prefix + "leave\n``` ```ConfusingBot will leave your channel```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void LeaveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "leave\n``` ```ConfusingBot will leave your channel```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void StoppedMusicMessage(TextChannel channel)
    {
        EmbedManager.SendCustomEmbed("Stopped Music\uD83C\uDFB6", "", Color.decode("#d400ff"), channel, 5);
    }

}
