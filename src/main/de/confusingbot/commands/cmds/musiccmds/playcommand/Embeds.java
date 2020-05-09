package main.de.confusingbot.commands.cmds.musiccmds.playcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
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
