package main.de.confusingbot.commands.cmds.defaultcmds.memecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
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
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "meme\n``` ```Shows you a Meme```", channel, EmbedsUtil.showUsageTime);
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
