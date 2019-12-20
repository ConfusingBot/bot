package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "message\n``` " +
                "```You can create welcome/leave messages will be sent if a member join/leave the server```\n");
    }

}
