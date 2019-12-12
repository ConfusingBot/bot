package main.de.confusingbot.commands.cmds.musiccmds.joincommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "join\n``` ```ConfusingBot will join your channel```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JoinUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "join`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void YourQueueIsEmptyInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`Ups, your Queue is empty`⚠️", channel, EmbedsUtil.showInfoTime);
    }

}
