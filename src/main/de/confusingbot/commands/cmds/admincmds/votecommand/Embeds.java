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
    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "vote\n```" +
                "```You can create special VoteMessages where member can vote for a specific thing!```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "vote create [#channel] [time in hours] [Header] 1: [text1] 2: [text2] [...]\n```"
                + "```Create a Vote Message with up to 9 vote points!```"
                + "```yaml\n" + Main.prefix + "vote remove [messageID]\n```"
                + "```Remove the Vote form the tempchannels```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "vote create [#channel] [time in hours] [Header] 1: [text1] 2: [text2] [...]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "vote remove [messageID]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoMentionedTextChannelError(TextChannel channel){
        EmbedManager.SendErrorEmbed("You haven't mentioned a TextChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTimeInHours(TextChannel channel){
        EmbedManager.SendErrorEmbed("You haven't mentioned a exist Time!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyRemovedTempchannel(TextChannel channel, String name)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "**" + name + "**", "the TempChannel!");
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void HasNoTempChannelInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no Tempchannels**! \nYou can add TempChannels with`" + Main.prefix + "tempchannel add [id]`", channel, 5);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public long SendVoteEmbed(TextChannel channel, String title, String voteText, int timeInHours)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle(title);
        builder.setDescription(voteText);
        builder.setFooter("Time To Vote: " + timeInHours + " hours");

        return EmbedManager.SendEmbedGetMessageID(builder, channel);
    }

    public void SendResultEmbed(TextChannel channel, String result)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("Result");
        builder.setDescription(result);

         EmbedManager.SendEmbed(builder, channel, 0);
    }

}
