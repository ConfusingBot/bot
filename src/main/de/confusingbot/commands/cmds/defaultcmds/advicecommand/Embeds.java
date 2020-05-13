package main.de.confusingbot.commands.cmds.defaultcmds.advicecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.person.Person;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "advice\n``` ```Shows you an Advice```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void AdviceUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "advice\n``` ```Shows you an Advice```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void SendSomethingWentWrong(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Something went wrong :/", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendAdvice(TextChannel channel, String advice, String color, Person person)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(person.name, null, person.imageUrl);
        builder.setColor(Color.decode(color));
        builder.setDescription(advice);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

}
