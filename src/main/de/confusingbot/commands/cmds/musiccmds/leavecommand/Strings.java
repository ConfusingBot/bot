package main.de.confusingbot.commands.cmds.musiccmds.leavecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Strings
{
    public Strings()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "preview [your text]\n``` ```Preview your text in a box```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void LeaveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "`leave`", channel, StringsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void AreNotInBotVoiceChannelError(TextChannel channel){
        EmbedManager.SendErrorEmbed("You have to be in the VoiceChannel wehere also the bot is in!", channel, StringsUtil.showErrorTime);
    }

    public void BotIsNotInVoiceChannelERror(TextChannel channel){
        EmbedManager.SendErrorEmbed("The bot is in no VoiceChannel!", channel, StringsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public void StoppedMusicMessage(TextChannel channel){
        EmbedManager.SendCustomEmbed("Stopped Music\uD83C\uDFB6", "", Color.decode("#d400ff"), channel, 5);
    }

}
