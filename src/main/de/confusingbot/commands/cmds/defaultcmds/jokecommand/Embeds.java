package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "joke ([Mother, JackNorris])\n``` ```ConfusingBot will make you laugh```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JokeUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "joke ([JackNorris, Mother])`", channel, EmbedsUtil.showUsageTime);
    }

}
