package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

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
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "roleborder create [roleName]`\n`"
                + "`" + Main.prefix + "roleborder remove [@role]`\n`"
                + Main.prefix + "roleborder add [@role]`", channel, StringsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "roleborder add [@role]`", channel, StringsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "roleborder create [roleName]`", channel, StringsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "roleborder remove [@role]`", channel, StringsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    public void RoleBorderAlreadyExistsError(TextChannel channel)
    {
        StringsUtil.AlreadyExistsError(channel, "RoleBorder");
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        StringsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void RoleDoesNotExistError(TextChannel channel)
    {
        StringsUtil.NotExistingError(channel, "This role");
    }


    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyCreateRoleBorder(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully create the role **" + name + "**", channel, StringsUtil.showUsageTime);
    }

    public void SuccessfullyAddedRoleBorder(TextChannel channel, String roleName)
    {
        StringsUtil.SuccessfulAdded(channel, roleName, "the RoleBorders");
    }

    public void SuccessfullyRemovedRoleBorder(TextChannel channel, String roleName)
    {
        StringsUtil.SuccessfulRemoved(channel, roleName, "from the RoleBorders");
    }


}
