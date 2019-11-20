package main.de.confusingbot.commands.cmds.admincmds.clearcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.nio.channels.Channel;
import java.util.List;

public class Strings
{

    public Strings()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "clear [# of messages]\n``` ```Clear the last # messages```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ClearUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "clear [# messages]`", channel, 5);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidNumberError(TextChannel channel, String number)
    {
        StringsUtil.NoNumberError(channel, number);
    }

    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulRemovedXMessages(TextChannel channel, List<Message> messages)
    {
        StringsUtil.SuccessfulRemoved(channel, (messages.size() - 1) + " messages", "this server");
    }
}
