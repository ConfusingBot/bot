package main.de.confusingbot.commands.cmds.ownercmds.customonetimeembedcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void Usage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "onetimeembed`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

}
