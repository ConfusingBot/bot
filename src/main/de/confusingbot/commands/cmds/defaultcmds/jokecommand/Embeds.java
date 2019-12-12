package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "joke ([mother, jacknorris])\n``` ```ConfusingBot will make you laugh```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JokeUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "joke ([mother, jacknorris]`", channel, EmbedsUtil.showUsageTime);
    }

}
