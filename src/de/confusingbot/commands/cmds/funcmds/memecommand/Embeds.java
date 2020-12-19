package de.confusingbot.commands.cmds.funcmds.memecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.fun.add("```yaml\n" + Main.prefix + "meme\n``` ```Shows you a Meme```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void MemeUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "meme\n``` ```Shows you a Meme```", channel, EmbedsUtil.showUsageTime);
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
        return EmbedsUtil.SendWaitMessage(channel, "Searching good Meme..");
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendMeme(TextChannel channel, String title, String imageUrl, String url, String color)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Reddit", null, "https://ralfw.de/wp-content/uploads/2019/05/redditlogo-300x300.png");
        builder.setColor(Color.decode(color));
        builder.setImage(imageUrl);
        builder.setTitle(title, url);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

}
