package de.confusingbot.commands.cmds.admincmds.messagecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

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
                "```yaml\n" + Main.prefix + "message add [welcome/leave] [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember/@leaveMember) to the server look at (#rule)]\n``` " +
                        "```Create a welcome/leave message which will be sent if member join/leave the server```" +
                        "```yaml\n" + Main.prefix + "message add welcome private ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember/@leaveMember) to the server look at (#rule)]\n``` " +
                        "```Create a private welcome message which will be sent as an direct message if the member join the server```" +
                        "```yaml\n" + Main.prefix + "message remove [welcome/leave] (private)\n```" +
                        "```Remove the welcome/leave message```", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageAddUsage(TextChannel channel, String messagetype, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "message add [" + messagetype + "]" + privateString +" [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: [Welcome (@newMember/@leaveMember) to the server look at (#rule)]\n``` " +
                "```Create a " + messagetype + " message which will be sent if member " + (messagetype.equals("welcome") ? "join" : "leave") + " the server```", channel, EmbedsUtil.showUsageTime);
    }

    public void MessageRemoveUsage(TextChannel channel, String messagetype)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "message remove [" + messagetype + "] (private)\n```" +
                "```Remove the " + messagetype + " message```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void NoMessageDefinedError(TextChannel channel, String messageType)
    {
        EmbedManager.SendErrorEmbed("You have no **" + messageType + "** message defined!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoMessageChannelMentionedError(TextChannel channel, String messageType)
    {
        EmbedManager.SendErrorEmbed("You have not mentioned **" + messageType + "** message channel!", channel, EmbedsUtil.showErrorTime);
    }

    public void MessageAlreadyExistsError(TextChannel channel, String messageType)
    {
        EmbedsUtil.AlreadyExistsError(channel, messageType + " message");
    }

    public void MessageDoesNotExistsError(TextChannel channel, String messageType)
    {
        EmbedsUtil.NotExistingError(channel, messageType + " message");
    }

    public void CouldNotFindMessageChannelError(TextChannel channel, String messageType)
    {
        EmbedManager.SendErrorEmbed("The **" + messageType + "** message channel does not **exist**!", channel, EmbedsUtil.showErrorTime);
    }

    public void CanNotSendPrivateLeaveMessage(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("I am sry but I **can't send private leave** messages :/", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedMessage(TextChannel channel, String messageType, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendSuccessEmbed("You successfully added a **" + messageType + "" + privateString + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedMessage(TextChannel channel, String messageType, boolean isPrivate)
    {
        String privateString = isPrivate ? " private" : "";
        EmbedManager.SendSuccessEmbed("You successfully removed a **" + messageType + "" + privateString + " message**",
                channel, EmbedsUtil.showSuccessTime);
    }
}
