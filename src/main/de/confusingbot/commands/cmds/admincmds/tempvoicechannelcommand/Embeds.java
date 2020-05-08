package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageManager;
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
        HelpManager.admin.add("```yaml\n" + Main.prefix + "tempchannel\n```" +
                "```Create special VoiceChannels, which automatically creates a new VoiceChannel by joining it(named after the joining user)```" +
                "```fix\n" + TempVoiceChannelCommandManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=5)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "tempchannel add [VoiceChannelID]\n```"
                + "```Creates a special VoiceChannel, which automatically creates a new VoiceChannel by joining it```"
                + "```yaml\n" + Main.prefix + "tempchannel remove [VoiceChannelID]\n```"
                + "```Remove the VoiceChannel form the tempchannels```"
                + "```yaml\n" + Main.prefix + "tempchannel list\n```"
                + "```List all tempchannels of this server```", channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "tempchannel add [channelId]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "tempchannel remove [channelId]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void VoiceChannelAlreadyExistsError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("This VoiceChannel is already a TempChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void CouldNotFindVoiceChannelByIDError(TextChannel channel, long channelid)
    {
        EmbedManager.SendErrorEmbed("Couldn't find VoiceChannel by ID: **" + channelid + "**", channel, EmbedsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void NoValidVoiceChannelIDNumberError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoExistingTempChannelError(TextChannel channel, long channelid)
    {
        EmbedsUtil.NotExistingError(channel, "VoiceChannel with the ID " + channelid);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedTempchannel(TextChannel channel, String name)
    {
        EmbedsUtil.SuccessfulAdded(channel, name, "this server as an **TempChannel**");
    }

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
    public void SendTempVoiceChannelListEmbed(TextChannel channel, String description){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("⏳TempChannels: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
