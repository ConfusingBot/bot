package main.de.confusingbot.commands.cmds.musiccmds.volumecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "volume\n``` ```Changes the volume```");
    }
}
