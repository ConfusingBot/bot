package main.de.confusingbot.commands.cmds.admincmds.bumpbotcommand;

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
        EmbedManager.SendUsageEmbed( "`" + Main.prefix + "bump add/remove`", channel, StringsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "bump add [#channel] [bumpcommand]`", channel, StringsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "bump remove`", channel, StringsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel){
        StringsUtil.NoPermissionError(channel);
    }

    public void OnlyOneAllowedBumpCommandAllowedError(TextChannel channel){
        StringsUtil.OnlyOneAllowedToExistError(channel, "BumpCommand");
    }

    public void HaveNotMentionedATextChannelError(TextChannel channel){
        StringsUtil.HavenNotMentionedError(channel, "#channel");
    }

    public void NoExistingBumpCommandError(TextChannel channel){
        StringsUtil.NotExistingError(channel, "BumpCommand");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedBumpCommand(TextChannel channel){
        StringsUtil.SuccessfulAdded(channel, "BumpCommand", "this server");
    }

    public void SuccessfulRemovedBumpCommand(TextChannel channel){
        StringsUtil.SuccessfulRemoved(channel, "BumpCommand", "this server");
    }
}
