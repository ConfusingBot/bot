package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.EmbedsUtil;
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
        HelpManager.admin.add("```yaml\n" + Main.prefix + "repeatinfo\n``` " +
                "```fix\n" + RepeatInfoCommandManager.permission.name() + "\n```" +
                "```Create a text which repeats every timestap!```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "repeatinfo add [#channel] [timestep] ([color]) ([title] " + RepeatInfoCommandManager.infoKey + ") [info]\n```"
                        + "```Add a RepeatInfo to this server which shows every [timestep]```"
                        + "```yaml\n" + Main.prefix + "repeatinfo remove [index See repeatinfo list]\n``` "
                        + "```Remove the RepeatInfo a the index```"
                        + "```yaml\n" + Main.prefix + "repeatinfo list\n``` "
                        + "```List all of the RepeatInfos of this server and offers you the index!```"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "repeatinfo add [#channel] [timestep] ([color]) ([title] " + RepeatInfoCommandManager.infoKey + ") [info]\n```"
                + "```Add a RepeatInfo to this server which shows every [timestep]```", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "repeatinfo remove [index See repeatinfo list]\n``` "
                + "```Remove the RepeatInfo a the index```", channel, EmbedsUtil.showUsageTime);
    }

    public void ListUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "repeatinfo list\n``` "
                + "```List all of the RepeatInfos of this server and offers you the index!```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void OnlyXAllowedInfoCommandsError(TextChannel channel, int maxInfos)
    {
        EmbedManager.SendUsageEmbed("A server can max has " + maxInfos + " RepeatInfos", channel, EmbedsUtil.showErrorTime);
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

    public void NoExistingRepeatInfosInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This server has no RepeatInfos!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTimeError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a Time!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void ToLargeGapBetweenRepeatsInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("The max allowed gap between the information's is " + RepeatInfoCommandManager.maxGapInHours + "h !", channel, EmbedsUtil.showInfoTime);
    }


    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedRepeatInfo(TextChannel channel)
    {
        EmbedsUtil.SuccessfulAdded(channel, "RepeatInfo", "this server");
    }

    public void SuccessfulRemovedRepeatInfo(TextChannel channel, int index)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "RepeatInfo", "this server at index: " + index);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void ListRepeatInfosEmbed(TextChannel channel, String text)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("RepeatInfos: ");
        builder.setDescription(text);

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showInfoTime);
    }

    public void SendInfoEmbed(TextChannel channel, String color, String title, String info)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(color));
        builder.setTitle(title);
        builder.setDescription(info);

        EmbedManager.SendEmbed(builder, channel, 0);
    }

}
