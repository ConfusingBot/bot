package main.de.confusingbot.commands.cmds.defaultcmds.clientinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.commands.CommandManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class Strings
{

    public Strings(){
        HelpManager.useful.add("```yaml\n" + Main.prefix + "clientinfo [@User]\n``` ```Give you some informations about [@User]```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void ClientInfoUsage(TextChannel channel){
        EmbedManager.SendInfoEmbed( "`" + Main.prefix + "clientinfo [@client]`", channel, StringsUtil.showUsageTime);
    }

}
