package de.confusingbot.commands.cmds.musiccmds.playcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "play [song/playlist Title/URL]\n``` " +
                "```Start playing a track or add additional songs to the queue```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void PlayUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "play [song/playlist Title/URL]\n``` " +
                "```Start playing a track or add additional songs to the queue```", channel, EmbedsUtil.showUsageTime);
    }

}
