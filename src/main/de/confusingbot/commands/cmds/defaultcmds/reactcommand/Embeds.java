package main.de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    public Embeds()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "react [textchannel] [messageID] [Emotji's]\n``` ```React with the [Emotji's] on a message[messageID]\uD83D\uDC39```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ReactCommandUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" +  Main.prefix + "react [channel] [message id] [emotjis]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoMessageIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void TriedToAddNoValidEmote(TextChannel channel){
        EmbedManager.SendUsageEmbed("You tried to add a no valid emote!", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedEmotes(TextChannel channel, String emotesString)
    {
        EmbedManager.SendSuccessEmbed("You successfully added:\n " + emotesString, channel, 5);
    }


}
