package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

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
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "repeatinfo remove [index See repeatinfo list]`", channel, EmbedsUtil.showUsageTime);
    }

    public void ListUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "repeatinfo list`", channel, EmbedsUtil.showUsageTime);
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

    public void NoMentionedTextChannelError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "#channel");
    }

    public void NoExistingInfoAtIndexError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "RepeatInfo");
    }

    public void NoValidNumberError(TextChannel channel, String number)
    {
        EmbedsUtil.NoNumberError(channel, number);
    }

    public void NoExistingRepeatInfos(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("This server has no RepeatInfos!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTimeError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a Time!", channel, EmbedsUtil.showErrorTime);
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

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void ListRepeatInfosEmbed(TextChannel channel, String text){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("RepeatInfos: ");
        builder.setDescription(text);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
