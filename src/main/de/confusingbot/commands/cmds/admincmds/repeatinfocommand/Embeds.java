package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {

    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "repeatinfo add/remove`", channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "repeatinfo add [#channel] [info]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "repeatinfo remove`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void OnlyXAllowedInfoCommandsError(TextChannel channel, int maxInfos)
    {
        EmbedManager.SendUsageEmbed("A server can max has " + maxInfos + " RepeatInfos", channel, EmbedsUtil.showUsageTime);
    }

    public void HaveNotMentionedATextChannelError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "#channel");
    }

    public void NoExistingInfoAtIndexError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "BumpCommand");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedBumpCommand(TextChannel channel)
    {
        EmbedsUtil.SuccessfulAdded(channel, "RepeatInfo", "this server");
    }

    public void SuccessfulRemovedRepeatInfo(TextChannel channel)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "RepeatInfo", "this server");
    }
}
