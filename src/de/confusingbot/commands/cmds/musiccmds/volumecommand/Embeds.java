package de.confusingbot.commands.cmds.musiccmds.volumecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "volume\n``` ```Changes the volume```");
    }
}
