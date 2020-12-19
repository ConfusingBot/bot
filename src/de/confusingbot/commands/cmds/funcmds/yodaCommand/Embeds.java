package de.confusingbot.commands.cmds.funcmds.yodaCommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.person.Person;
import de.confusingbot.manage.person.PersonManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "yoda [text]\n``` ```Translates text into Yoda Language```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void YodaUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "yoda [text]\n``` ```Translates text into Yoda Language```", channel, EmbedsUtil.showUsageTime);
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
        return EmbedsUtil.SendWaitMessage(channel, "Translating into Yodish..");
    }

    public void SendNoValidLetterWasUsed(TextChannel channel){
        EmbedManager.SendInfoEmbed("You used a no valid Letter!", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendYodaText(TextChannel channel, String text)
    {
        Person person = PersonManager.getPerson("yoda");
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(person.name, null, person.imageUrl);
        builder.setColor(Color.decode("#00ff00"));
        builder.setDescription(text);

        EmbedManager.SendEmbed(builder, channel, 30);
    }
}
