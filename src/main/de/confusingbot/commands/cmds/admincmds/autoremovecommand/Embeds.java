package main.de.confusingbot.commands.cmds.admincmds.autoremovecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.clearcommand.ClearCommandManager;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "autoremove\n``` ```Disable/Enable the autoremove feature of the ConfusingBot```" +
                "```fix\n" + ClearCommandManager.permission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void AutoRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "autoremove\n``` ```Disable/Enable the autoremove feature of the ConfusingBot```" +
                "```fix\n" + ClearCommandManager.permission.name() + "\n```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void FeatureDoesNotExistYet(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This feature doesn't exist yet :/", channel, EmbedsUtil.showInfoTime);
    }
}
