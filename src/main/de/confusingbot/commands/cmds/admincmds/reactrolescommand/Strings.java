package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{
    public Strings()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "reactrole [channel] [messageID] [emote] [@role])\n``` " +
                "```▶️Create a [#channel] where you  write your RoleBoard [messageID]\n" +
                "▶️Think about a [emote] with which you want to get the [@role]```" +
                "```Add a emotji to you role board on which the user can click and automaticlly get the @role```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "reactrole add/remove`", channel, StringsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "reactrole add [channel] [messageID] [emote] [@role]`", channel, StringsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "reactrole remove [channel] [messageID] [emote] [@role]`", channel, StringsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    public void ReactRoleAlreadyExistsError(TextChannel channel)
    {
        StringsUtil.AlreadyExistsError(channel, "ReactRole");
    }

    public void ReactRoleNotExistsError(TextChannel channel)
    {
        StringsUtil.NotExistingError(channel, "ReactRole");
    }

    public void NoMessageIDError(TextChannel channel, String id)
    {
        StringsUtil.NoValidIDNumberError(channel, id);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedReactRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully added the @" + role.getName() + " to ReactRoles", channel, StringsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedReactRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully removed the @" + role.getName() + " to ReactRoles", channel, StringsUtil.showSuccessTime);
    }
}
