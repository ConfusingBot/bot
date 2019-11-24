package main.de.confusingbot.commands.cmds.admincmds.clearcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Embeds
{

    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "clear [# of messages]\n``` ```Clear the last # messages```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ClearUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "clear [# messages]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidNumberError(TextChannel channel, String number)
    {
        EmbedsUtil.NoNumberError(channel, number);
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulRemovedXMessages(TextChannel channel, List<Message> messages)
    {
        EmbedManager.SendSuccessEmbed("You successfully cleared " + (messages.size()) + " messages!", channel, EmbedsUtil.showSuccessTime);
    }
}
