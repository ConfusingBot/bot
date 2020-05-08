package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "message\n``` " +
                "```Create welcome/leave messages which will be sent if a member joined/left your server```" +
                "```fix\n" + MessageManager.permission.name() + "\n```" +
                "[[Example Video]](https://www.youtube.com/watch?v=M6z6gEaQ2_k&list=PLkI3ZL9zLpd4cUUzrwgawcN1Z3Wa6d7mm&index=8)\n");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "message [add] [welcome/leave] [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember/@leaveMember) to the server look at (#rule)]\n``` " +
                        "```Create a welcome/leave message which will be sent if member join/leave the server```" +
                        "```yaml\n" + Main.prefix + "message [add] welcome private ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember/@leaveMember) to the server look at (#rule)]\n``` " +
                        "```Create a private welcome message which will be sent as an direct message if the member join the server```" +
                        "```yaml\n" + Main.prefix + "message remove [welcome/leave] (private)\n```" +
                        "```Remove the welcome/leave message```", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageAddUsage(TextChannel channel, String messagetype, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message add " + messagetype + "" + privateString + " [#messageChannel] ([#hexColor]) ([Title]) MESSAGE: [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageRemoveUsage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message " + messagetype + " remove`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
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
        EmbedManager.SendErrorEmbed("The **" + messagetype + "** message channel does not **exist**!", channel, EmbedsUtil.showErrorTime);
    }

    public void CanNotSendPrivateLeaveMessage(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("I am sry but I **can't send private leave** messages :/", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedMessage(TextChannel channel, String messagetype, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendSuccessEmbed("You successfully added a **" + messagetype + "" + privateString + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedMessage(TextChannel channel, String messagetype, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendSuccessEmbed("You successfully removed a **" + messagetype + "" + privateString + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }
}
