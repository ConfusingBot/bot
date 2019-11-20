package main.de.confusingbot.commands.cmds.admincmds.bumpbotcommand;

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
        EmbedManager.SendUsageEmbed( "`" + Main.prefix + "bump add/remove`", channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "bump add [#channel] [bumpcommand]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "bump remove`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel){
        EmbedsUtil.NoPermissionError(channel);
    }

    public void OnlyOneAllowedBumpCommandAllowedError(TextChannel channel){
        EmbedsUtil.OnlyOneAllowedToExistError(channel, "BumpCommand");
    }

    public void HaveNotMentionedATextChannelError(TextChannel channel){
        EmbedsUtil.HavenNotMentionedError(channel, "#channel");
    }

    public void NoExistingBumpCommandError(TextChannel channel){
        EmbedsUtil.NotExistingError(channel, "BumpCommand");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedBumpCommand(TextChannel channel){
        EmbedsUtil.SuccessfulAdded(channel, "BumpCommand", "this server");
    }

    public void SuccessfulRemovedBumpCommand(TextChannel channel){
        EmbedsUtil.SuccessfulRemoved(channel, "BumpCommand", "this server");
    }
}
