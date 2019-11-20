package main.de.confusingbot.commands.cmds.admincmds.customonetimeembedcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Strings
{
    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void Usage(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`" + Main.prefix + "onetimeembed`", channel, 0);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel){
        StringsUtil.NoPermissionError(channel);
    }

}
