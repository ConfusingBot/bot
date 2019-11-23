package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "tempchannel add [VoiceChannelID]\n``` " +
                "```▶Create a VoiceChannel [VoiceChannelID]```" +
                "```Creates a special VoiceChannel, which automatically creates a new VoiceChannel by joining, which bears the name of the user```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "tempchannel add [channelId]`\n`"
                + Main.prefix + "tempchannel remove [channelId]`", channel, EmbedsUtil.showUsageTime);
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
        EmbedManager.SendErrorEmbed("This VoiceChannel already is a TempChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void CouldNotFindVoiceChannelByIDError(TextChannel channel, long channelid)
    {
        EmbedManager.SendErrorEmbed("Couldn't find VoiceChannel by ID: " + channelid, channel, EmbedsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
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


}
