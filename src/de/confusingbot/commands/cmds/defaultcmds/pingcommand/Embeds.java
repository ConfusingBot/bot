package de.confusingbot.commands.cmds.defaultcmds.pingcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "ping\n``` ```Show you the Ping!```");
    }
}
