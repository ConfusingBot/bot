package main.de.confusingbot.commands.cmds.musiccmds.joincommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;
import org.w3c.dom.Text;

public class Strings
{
    public Strings()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "preview [your text]\n``` ```Preview your text in a box```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JoinUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "join`", channel, StringsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void YourQueueIsEmptyInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`Ups, your Queue is empty`⚠️", channel, StringsUtil.showInfoTime);
    }

    public void YouAreNotInAVoiceChannelInformation(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`Ups, you aren't in a VoiceChannel\uD83C\uDF99`", channel, StringsUtil.showInfoTime);
    }

}
