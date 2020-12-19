package de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "reactrole\n``` " +
                "```Create a awesome role add/take away system```" +
                "```fix\n" + ReactRoleManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=2)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "reactrole add [channel] [messageID] [emoji] [@role]\n```"
                        + "```Add a emoji to a message on which the user can click to get a @role```"
                        + "```yaml\n" + Main.prefix + "reactrole remove [channel] [messageID] [emote] [@role]\n``` "
                        + "```Remove the reactrole(emoji) form the message```"
                        + "```yaml\n" + Main.prefix + "reactrole list\n``` "
                        + "```List all reactroles of this server```"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "reactrole add [channel] [messageID] [emoji] [@role]\n```"
                + "```Add a emoji to a message on which the user can click to get a @role```", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "reactrole remove [channel] [messageID] [emote] [@role]\n``` "
                + "```Remove the reactrole(emoji) form the message```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void ReactRoleAlreadyExistsError(TextChannel channel)
    {
        EmbedsUtil.AlreadyExistsError(channel, "ReactRole");
    }

    public void ReactRoleNotExistsError(TextChannel channel, String role)
    {
        EmbedsUtil.NotExistingError(channel, "ReactRole " + role);
    }

    public void NoValidIdError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void BotHasNoPermissionToAssignRole(TextChannel channel, Role role)
    {
        EmbedManager.SendErrorEmbed("The bot has no right to assign this " + role.getAsMention() + "\n" +
                "Please give the bot a role over the role to be assign this role!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoValidEmoteError(TextChannel channel, String emote)
    {
        EmbedManager.SendErrorEmbed("`" + emote + "` is no valid emote!", channel, EmbedsUtil.showErrorTime);
    }

    public void RoleDoesNotExistError(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The role with the id " + roleid + " doesn't exist on this server!", channel, EmbedsUtil.showErrorTime);
    }

    public void MessageIdDoesNotExistAnymore(TextChannel channel, String messageid)
    {
        EmbedManager.SendInfoEmbed("This **messageid " + messageid + "** doesn't exist anymore\nI will delete this ReactRole", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedReactRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully added @" + role.getName() + " to the ReactRoles", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedReactRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully removed @" + role.getName() + " from the ReactRoles", channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void HasNoReactRoleInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no ReactRoles**! \nYou can add ReactRoles with`" + Main.prefix + "reactrole add`", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendListEmbed(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51ReactRoles: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedManager.SendCustomEmbedGetMessageID("Please Wait", "This needs upto 10s!", Color.pink, channel);
    }

}
