package main.de.confusingbot.commands.cmds.defaultcmds.rulescommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{

    public Strings()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "rules\n``` ```Shows you the rules of this server```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void RulesCommandUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "rules`", channel, StringsUtil.showUsageTime);
    }

}
