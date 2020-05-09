package main.de.confusingbot.commands.cmds.admincmds.rolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "role\n``` " +
                "```Create or remove roles in a simple way```" +
                "```fix\n" + RoleCommandManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=4)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed(
                "```yaml\n" + Main.prefix + "role create [name] ([#color])\n```"
                        + "```Create a new role```"
                        + "```yaml\n" + Main.prefix + "role delete [@role]\n```"
                        + "```Delete the @role```"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "role create [name] ([#color])\n```"
                + "```Create a new role```", channel, EmbedsUtil.showUsageTime);
    }

    public void DeleteUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "role delete [@role]\n```"
                + "```Delete the @role```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void HaveNotMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void NoHexColorError(TextChannel channel, String hexColor)
    {
        EmbedManager.SendErrorEmbed("This is no HexColor: " + hexColor + "!", channel, EmbedsUtil.showErrorTime);
    }

    public void CouldNotDeleteRole(TextChannel channel, String role)
    {
        EmbedManager.SendErrorEmbed("Couldn't delete role " + role, channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyCreatedRole(TextChannel channel, String roleName, Color color)
    {
        EmbedManager.SendCustomEmbed("Create Role!", "You successfully created **@" + roleName + "**", color, channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyDeletedRole(TextChannel channel, String roleName)
    {
        EmbedsUtil.SuccessfulDeleted(channel, "@" + roleName, "this server");
    }
}
