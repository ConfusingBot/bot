package main.de.confusingbot.commands.cmds.admincmds.joinrole;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "joinrole``` " +
                "```Create a JoinRole which will be added to a member after he joined your server!```" +
                "```fix\n" + JoinRoleManager.permission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "joinrole add [@role]\n```"
                        + "```Add a JoinRole to your server```\n"
                        + "```yaml\n" + Main.prefix + "joinrole remove [@role]\n```"
                        + "```Remove the @role form the JoinRoles```\n"
                        + "```yaml\n" + Main.prefix + "joinrole list```"
                        + "```List all JoinRoles of this server```\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "joinrole add [@role]\n```"
                + "```Add a JoinRole to your server```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "joinrole remove [@role]\n```"
                + "```Remove the @role form the JoinRoles```\n", channel, EmbedsUtil.showUsageTime);
    }

    public void ListUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "joinrole list```"
                + "```List all JoinRoles of this server```\n", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void BotHasNoPermissionToAssignRole(TextChannel channel, Role role)
    {
        EmbedManager.SendErrorEmbed("The bot has no right to assign this " + role.getAsMention() + "\n" +
                "Please give the bot a role over the role to be assign this role!", channel, EmbedsUtil.showErrorTime);
    }

    public void RoleDoesNotExistError(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The role with the id **" + roleid + " doesn't exist** on this server!", channel, EmbedsUtil.showErrorTime);
    }

    public void AlreadyExistingJoinRoleError(TextChannel channel, Role role)
    {
        EmbedsUtil.AlreadyExistsError(channel, "JoinRole" + " (" + role.getAsMention() + ")");
    }

    public void NoExistingJoinRoleError(TextChannel channel, Role role)
    {
        EmbedsUtil.NotExistingError(channel, "JoinRole" + " (" + role.getAsMention() + ")");
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void HasNoJoinRoleInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no JoinRoles**! \nYou can add JoinRoles with `" + Main.prefix + "joinrole add`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulRemovedJoinRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully removed " + role.getAsMention() + " from the JoinRoles", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfulAddedJoinRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully added " + role.getAsMention() + " to the JoinRoles", channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendJoinRoleList(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51JoinRoles: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

}
