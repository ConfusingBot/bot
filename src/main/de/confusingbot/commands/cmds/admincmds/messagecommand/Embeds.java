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
    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "reactrole [channel] [messageID] [emote] [@role])\n``` " +
                "```▶️Create a [#channel] where you  write your RoleBoard [messageID]\n" +
                "▶️Think about a [emote] with which you want to get the [@role]```" +
                "```Add a emotji to you role board on which the user can click and automaticlly get the @role```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message [add/remove] [welcome/leave]`", channel, EmbedsUtil.showUsageTime);
    }

    public void WelcomeMessageAddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message add welcome [#messageChannel] ([#hexColor]) ([Title]) MESSAGE: [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
    }

    public void LeaveMessageAddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message add leave [#messageChannel] ([#hexColor]) ([Title]) MESSAGE: [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
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
        EmbedsUtil.AlreadyExistsError(channel, messagetype + "message");
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


    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void DefaultWelcomeMessage(TextChannel channel, TextChannel rules, Member member)
    {
        if (channel != null)
        {

        }
    }

}
