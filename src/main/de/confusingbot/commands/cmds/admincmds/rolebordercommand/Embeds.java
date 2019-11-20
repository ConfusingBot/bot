package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

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
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "roleborder create [roleName]`\n`"
                + "`" + Main.prefix + "roleborder remove [@role]`\n`"
                + Main.prefix + "roleborder add [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "roleborder add [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "roleborder create [roleName]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "roleborder remove [@role]`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void RoleBorderAlreadyExistsError(TextChannel channel)
    {
        EmbedsUtil.AlreadyExistsError(channel, "RoleBorder");
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void RoleDoesNotExistError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "This role");
    }


    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyCreateRoleBorder(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully create the role **" + name + "**", channel, EmbedsUtil.showUsageTime);
    }

    public void SuccessfullyAddedRoleBorder(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulAdded(channel, roleName, "the RoleBorders");
    }

    public void SuccessfullyRemovedRoleBorder(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulRemoved(channel, roleName, "from the RoleBorders");
    }


}
