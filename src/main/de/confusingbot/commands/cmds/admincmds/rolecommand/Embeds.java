package main.de.confusingbot.commands.cmds.admincmds.rolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] ([#HexColor])`\n`"
                + Main.prefix + "role delete [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] ([#color])`", channel, EmbedsUtil.showUsageTime);
    }

    public void DeleteUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] [#color]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulCreatedRole(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulCreated(channel, "@" + roleName);
    }

    public void SuccessfulDeletedRole(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulDeleted(channel, "@" + roleName, "this server");
    }
}
