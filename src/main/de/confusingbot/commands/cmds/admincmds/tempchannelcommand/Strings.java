package main.de.confusingbot.commands.cmds.admincmds.tempchannelcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{
    public Strings()
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
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "tempchannel add [id]`\n`"
                + Main.prefix + "tempchannel remove [id]`", channel, StringsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules not accepted] [@role rules accepted]`", channel, StringsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule remove`", channel, StringsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void VoiceChannelAlreadyExistsError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("This VoiceChannel already is a TempChannel!", channel, StringsUtil.showErrorTime);
    }

    public void CouldNotFindVoiceChannelByIDError(TextChannel channel, long channelid)
    {
        EmbedManager.SendErrorEmbed("Couldn't find VoiceChannel by ID: " + channelid, channel, StringsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    public void NoValidVoiceChannelIDNumberError(TextChannel channel, String id)
    {
        StringsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoExistingTempChannelError(TextChannel channel, long channelid)
    {
        StringsUtil.NotExistingError(channel, "VoiceChannel with the ID " + channelid);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedTempchannel(TextChannel channel, String name)
    {
        StringsUtil.SuccessfulAdded(channel, name, "this server as an **TempChannel**");
    }

    public void SuccessfullyRemovedTempchannel(TextChannel channel, String name)
    {
        StringsUtil.SuccessfulRemoved(channel, "**" + name + "**", "the TempChannel!");
    }


    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void HasNoTempChannelInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no Tempchannels**! \nYou can add TempChannels with`" + Main.prefix + "tempchannel add [id]`", channel, 5);
    }


}
