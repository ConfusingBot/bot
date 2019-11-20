package main.de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{

    public Strings()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "react [textchannel] [messageID] [Emotji's]\n``` ```React with the [Emotji's] on a message[messageID]\uD83D\uDC39```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ReactCommandUsage(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("`- react [channel] [message id] [emotjis]`", channel, StringsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoMessageIDError(TextChannel channel, String id)
    {
        StringsUtil.NoValidIDNumberError(channel, id);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedEmotes(TextChannel channel, String emotesString)
    {
        EmbedManager.SendSuccessEmbed("You successfully added " + emotesString, channel, StringsUtil.showSuccessTime);
    }


}
