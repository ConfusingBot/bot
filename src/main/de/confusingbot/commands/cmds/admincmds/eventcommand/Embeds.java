package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

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
        HelpManager.admin.add("```yaml\n" + Main.prefix + "message\n``` " +
                "```You can create welcome/leave messages will be sent if a member join/leave the server```\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "event create [#channel] [messageid] [color] [time] [takePartEmote] [eventName] ROLE:[eventRoleName]\n```"
                        + "```TODO```\n"
                        + "```yaml\n" + Main.prefix + "event remove [@eventRole]\n```"
                        + "```Remove Event which is connected with this role!```\n"
                        + "```yaml\n" + Main.prefix + "event list\n```"
                        + "```List all active Events of this guild!```\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void CreateUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "event create [#channel] [messageid] [color] [time] [takePartEmote] [eventName] ROLE:[eventRoleName]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "event remove [@eventRole]`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void NoValidEmoteError(TextChannel channel, String emote)
    {
        EmbedManager.SendErrorEmbed(emote + " is no valid emote!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedNamesError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned the EventName or the RoleName!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoSelectedColorError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't selected a color!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedTextChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a TextChannel!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedMessageIDError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You haven't mentioned a MessageId!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoExistingEventRoleError(TextChannel channel, String roleMentioned)
    {
        EmbedManager.SendErrorEmbed(roleMentioned + " is no EventRole!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "role");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedEvent(TextChannel channel, Color color, String eventName)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Event");
        builder.setColor(color);
        builder.setDescription("Event called `" + eventName + "` has been created!");

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedEvent(TextChannel channel, Color color, String eventName)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Event");
        builder.setColor(color);
        builder.setDescription("Event called `" + eventName + "` has been removed!");

        EmbedManager.SendEmbed(builder, channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void HasNoEventsInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This guild has **no Events**! \nYou can create Events with`" + Main.prefix + "event create`", channel, 5);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendListEmbed(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51Events: ");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedManager.SendCustomEmbedGetMessageID("Please Wait", "This needs upto 10s!", Color.pink, channel);
    }
}
