package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "vote\n```" +
                "```You can create special VoteMessages where member can vote for a specific thing!```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "vote create [#channel] [time in hours] [Header] -1- [text1] -2- [text2] [...] ([@allowedRole @allowedRole2 ..])\n```"
                        + "```Create a Vote Message with up to 9 vote points!```"
                        + "```yaml\n" + Main.prefix + "vote remove [messageID]\n```"
                        + "```Remove the Vote form the tempchannels```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "vote create [#channel] [time in hours] [Header] -1- [text1] -2- [text2] [...] ([@allowedRole @allowedRole2 ..])`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "vote remove [messageID]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoMentionedTextChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a TextChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTimeInHours(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a exist Time!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void NoEmoteError(TextChannel channel, String emote)
    {
        EmbedManager.SendErrorEmbed("**" + emote + "** is no valid Emote!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public long SendVoteEmbed(TextChannel channel, String title, String voteText, long timeInHours)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle(title);
        builder.setDescription(voteText);
        builder.setFooter("Time To Vote: " + timeInHours + " hours");

        return EmbedManager.SendEmbedGetMessageID(builder, channel);
    }

    public void SendResultEmbed(TextChannel channel, String title, String result)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("Result form " + title);
        builder.setDescription(result);

        EmbedManager.SendEmbed(builder, channel, 0);
    }

}
