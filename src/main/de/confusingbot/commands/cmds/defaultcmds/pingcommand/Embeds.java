package main.de.confusingbot.commands.cmds.defaultcmds.pingcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "ping\n``` ```Show you the Ping!```");
    }
}
