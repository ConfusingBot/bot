package main.de.confusingbot.commands.cmds.admincmds.joinrole;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "joinrole``` " +
                "```Create a JoinRole which will be added to a member after he had joined your server!```\n"
               );
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "joinrole add [@role]\n```"
                +"```Add a JoinRole to your server```\n"
                + "```yaml\n" + Main.prefix + "joinrole remove [@role]\n```"
                +  "```Remove the @role form the JoinRoles```\n"
                        + "```yaml\n" + Main.prefix + "joinrole listn```"
                        +  "```List all JoinRoles of this server``\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "joinrole add [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "joinrole remove [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void ListUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "joinrole list`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void AlreadyExistingJoinRoleInformation(TextChannel channel, Role role){

    }

    public void NoExistingJoinRoleInformation(TextChannel channel, Role role){

    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedJoinRole(TextChannel channel)
    {
        EmbedsUtil.SuccessfulAdded(channel, "JoinRole", "this server");
    }

    public void SuccessfulRemovedJoinRole(TextChannel channel)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "JoinRole", "this server");
    }

}
