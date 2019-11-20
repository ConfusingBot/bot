package main.de.confusingbot.commands.cmds.musiccmds.pausecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "pause\n``` ```Pause/Resume the current Song```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void PauseUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "pause`", channel, 5);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPlayingTrackError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("The bot plays no music! \n Use `" + Main.prefix + "play [Url/SongName]` for starting a song!", channel, EmbedsUtil.showErrorTime);
    }
}
