package main.de.confusingbot.commands.cmds.admincmds.rolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{
    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] ([#HexColor])`\n`"
                + Main.prefix + "role delete [@role]`", channel, StringsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] ([#color])`", channel, StringsUtil.showUsageTime);
    }

    public void DeleteUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "role create [roleName] [#color]`", channel, StringsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        StringsUtil.HavenNotMentionedError(channel, "@role");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulCreatedRole(TextChannel channel, String roleName)
    {
        StringsUtil.SuccessfulCreated(channel, "@" + roleName);
    }

    public void SuccessfulDeletedRole(TextChannel channel, String roleName)
    {
        StringsUtil.SuccessfulDeleted(channel, "@" + roleName, "this server");
    }
}
