package main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.QuestionManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "youtube\n```" +
                " ```...```\n");

        HelpManager.admin.add("```yaml\n" + Main.prefix + "youtube\n```" +
                " ```...```" +
                "```fix\n" + QuestionManager.questionCategoryPermission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {

    }

    public void YouTubeNowUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "youtube now [YouTube channel id]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidYouTubeIdError(TextChannel channel, String id)
    {
        EmbedManager.SendErrorEmbed(id + " is no valid **YouTubeChannel** Id!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendVideoEmbed(TextChannel channel, String url, String title, String thumbnailUrl, LocalDateTime publishedAt, String uploaderName)
    {
        EmbedBuilder builder = new EmbedBuilder();
       DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        builder.setAuthor("YouTube");
        builder.setTitle(uploaderName);
        builder.setDescription("[**" + title + "**](" + url + ")");
        builder.addField("Date", dateFormatter.format(publishedAt), true);
        builder.addField("Time", timeFormatter.format(publishedAt), true);

        builder.setColor(Color.RED);

        builder.setImage(thumbnailUrl);

        //Message
        channel.sendMessage(builder.build()).queue();

    }
}
