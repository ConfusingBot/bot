package main.de.confusingbot.commands.cmds.admincmds.customonetimeembedcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
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
        EmbedsUtil.NoPermissionError(channel);
    }

}
