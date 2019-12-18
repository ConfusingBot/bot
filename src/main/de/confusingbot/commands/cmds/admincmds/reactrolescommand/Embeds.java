package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "reactrole\n``` " +
                "```Create a awesome role add/take away system```");
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
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "reactrole add [channel] [messageID] [emote] [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "reactrole remove [channel] [messageID] [emote] [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void ReactRoleAlreadyExistsError(TextChannel channel)
    {
        EmbedsUtil.AlreadyExistsError(channel, "ReactRole");
    }

    public void ReactRoleNotExistsError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "ReactRole");
    }

    public void NoMessageIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void BotHasNoPermissionToAssignRole(TextChannel channel, Role role)
    {
        EmbedManager.SendErrorEmbed("The bot has no right to assign this " + role.getAsMention() + "\n" +
                "Please give the bot a role over the role to be assign this role!", channel, EmbedsUtil.showErrorTime);
    }

    public void YouHaveNotMentionedAValidEmoteError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned valid a Emote!", channel, EmbedsUtil.showErrorTime);
    }

    public void RoleDoesNotExistError(TextChannel channel, long roleid){
        EmbedManager.SendErrorEmbed("The role with the id " + roleid + " doesn't exist on this server!", channel, EmbedsUtil.showErrorTime);
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
        EmbedManager.SendInfoEmbed("This guild has **no ReactRoles**! \nYou can add ReactRoles with`" + Main.prefix + "reactrole add`", channel, 5);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendReactRoleListEmbed(TextChannel channel, String description){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51ReactRoles: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
