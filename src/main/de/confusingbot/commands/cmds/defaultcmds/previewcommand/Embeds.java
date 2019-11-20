package main.de.confusingbot.commands.cmds.defaultcmds.previewcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "preview [your text]\n``` ```Preview your text in a box```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void PreviewUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`- preview [text]`", channel, EmbedsUtil.showUsageTime);
    }

}
