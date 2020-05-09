package main.de.confusingbot.commands.cmds.admincmds.clearcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "clear [# of messages]\n``` ```Clear the last # messages```" +
                "```fix\n" + ClearCommandManager.permission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ClearUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "clear [# of messages]\n``` ```Clear the last # messages```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidNumberError(TextChannel channel, String number)
    {
        EmbedsUtil.NoNumberError(channel, number);
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void NegativeNumberError(TextChannel channel)
    {
        //Message
        EmbedManager.SendMessage("really?", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void MaxNumberInformation(TextChannel channel, int number)
    {
        EmbedManager.SendInfoEmbed("**Delete this channel** if you want to clear it complete!", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulRemovedXMessages(TextChannel channel, List<Message> messages)
    {
        EmbedManager.SendSuccessEmbed("You successfully cleared **" + (messages.size() - 1) + " messages**!", channel, EmbedsUtil.showSuccessTime);
    }
}
