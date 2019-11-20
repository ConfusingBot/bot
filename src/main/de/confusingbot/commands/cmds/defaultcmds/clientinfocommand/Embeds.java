package main.de.confusingbot.commands.cmds.defaultcmds.clientinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    public Embeds(){
        HelpManager.useful.add("```yaml\n" + Main.prefix + "clientinfo [@User]\n``` ```Give you some informations about [@User]```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ClientInfoUsage(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`" + Main.prefix + "clientinfo [@client]`", channel, EmbedsUtil.showUsageTime);
    }

}
