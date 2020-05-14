package main.de.confusingbot.commands.cmds.funcmds.jokecommand;

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
        HelpManager.fun.add("```yaml\n" + Main.prefix + "joke ([momma, programming, chucknorris])\n``` ```Shows you a Joke```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void JokeUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "joke ([momma, programming, chucknorris])\n``` ```Shows you a Joke```", channel, EmbedsUtil.showUsageTime);
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
        return EmbedsUtil.SendWaitMessage(channel, "Searching good Joke..");
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendJoke(TextChannel channel, String setup, String punchline, String color, Person person)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor(person.name, null, person.imageUrl);
        builder.setColor(Color.decode(color));

        if (setup != null)
            builder.addField(setup, punchline, false);
        else
            builder.setDescription(punchline);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

}
