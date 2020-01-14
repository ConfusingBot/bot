package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageManager;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "roleborder\n``` " +
                "```Create RoleBorders which can be used as an seperator between roles```" +
                "```fix\n" + RoleBorderCommandManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=3)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "roleborder create [roleName]\n```"
                        + "```Create a RoleBorder which can be used as an seperator between roles```"
                        + "```yaml\nAdd a @role to the RoleBorders\n```"
                        + "```Add @role to the RoleBorders```"
                        + "```yaml\n" + Main.prefix + "roleborder remove [@role]\n```"
                        + "```Remove the @role form the roleborders```"
                        + "```yaml\n" + Main.prefix + "roleborder list\n```"
                        + "```List all roleborders of this server```", channel, EmbedsUtil.showUsageTime);
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
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
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
        EmbedsUtil.NotExistingError(channel, "This roleborder");
    }

    public void RoleBorderNameIsToLongError(TextChannel channel, String name)
    {
        EmbedManager.SendErrorEmbed("You role name(**" + name + "**) is  to long!", channel, EmbedsUtil.showUsageTime);
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
        EmbedsUtil.SuccessfulRemoved(channel, roleName, "the RoleBorders");
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void HasNoRoleBordersInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no RoleBorders**! \nYou can add RoleBorders with`" + Main.prefix + "roleborder add [@role]`", channel, 5);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendRoleBorderListEmbed(TextChannel channel, String description){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDD27RoleBorders: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
