package de.confusingbot.commands.cmds.defaultcmds.previewcommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "preview ([#color]) ([title] MESSAGE:) [text]\n``` ```Preview your text in a box```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void PreviewUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "preview ([#color]) ([title] MESSAGE:) [text]\n``` ```Preview your text in a box```", channel, EmbedsUtil.showUsageTime);
    }

}
