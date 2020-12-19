package de.confusingbot.commands.cmds.funcmds.advicecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.person.Person;
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
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "advice\n``` ```Shows you an Advice```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void SendSomethingWentWrong(TextChannel channel, int errorCode)
    {
        EmbedsUtil.SendSomethingWentWrong(channel, errorCode);
    }


    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public long SendWaitMessage(TextChannel channel)
    {
        return EmbedsUtil.SendWaitMessage(channel, "Searching for Person with Advice..");
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
