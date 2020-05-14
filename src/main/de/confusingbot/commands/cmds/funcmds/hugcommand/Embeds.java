package main.de.confusingbot.commands.cmds.funcmds.hugcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "hug\n``` ```Maybe ConfusingBot will hug you```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void HugCommandUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "hug\n``` ```Maybe ConfusingBot will hug you```", channel, EmbedsUtil.showUsageTime);
    }
}
