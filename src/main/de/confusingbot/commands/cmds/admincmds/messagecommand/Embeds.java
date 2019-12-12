package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
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
                "```yaml\n" + Main.prefix + "message [add] [welcome/leave] [#channel]  ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember) to the server look at (#rule)]\n``` " +
                        "```Create a welcome/leave message which will be sent if member join/leave the server```" +
                        "```yaml\n" + Main.prefix + "message remove [welcome/leave]\n```" +
                        "```Remove the welcome/leave message```", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageAddUsage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message add " + messagetype + " [#messageChannel] ([#hexColor]) ([Title]) MESSAGE: [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageRemoveUsage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message " + messagetype + " remove`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    public void NoMessageDefinedError(TextChannel channel, String messagetype)
    {
        EmbedManager.SendErrorEmbed("You have no **" + messagetype + "** message defined!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMessageChannelMentionedError(TextChannel channel, String messagetype)
    {
        EmbedManager.SendErrorEmbed("You have not mentioned **" + messagetype + "** message channel!", channel, EmbedsUtil.showErrorTime);
    }

    public void MessageAlreadyExistsError(TextChannel channel, String messagetype)
    {
        EmbedsUtil.AlreadyExistsError(channel, messagetype + " message");
    }

    public void MessageDoesNotExistsError(TextChannel channel, String messagetype)
    {
        EmbedsUtil.NotExistingError(channel, messagetype + " message");
    }

    public void CouldNotFindMessageChannelError(TextChannel channel, String messagetype)
    {
        EmbedManager.SendErrorEmbed("The **" + messagetype + "** message channel does not **exists**!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedMessage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendSuccessEmbed("You successfully added a **" + messagetype + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedMessage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendSuccessEmbed("You successfully removed a **" + messagetype + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }
}
