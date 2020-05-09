package main.de.confusingbot.commands.cmds.admincmds.autoremovecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.clearcommand.ClearCommandManager;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
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
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "autoremove\n``` ```Disable/Enable the autoremove feature of the ConfusingBot```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void AutoRemoveSetStatus(TextChannel channel, boolean newStatus)
    {
        EmbedManager.SendInfoEmbed("AutoRemove has been set to `" + newStatus + "`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }
}
