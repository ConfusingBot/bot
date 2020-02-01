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
                " ```Show you some useful stuff from YouTube```\n");

        HelpManager.admin.add("```yaml\n" + Main.prefix + "youtube\n```" +
                " ```...```" +
                "```fix\n" + QuestionManager.questionCategoryPermission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "youtube new [YouTube channel id]\n```"
                        + "```Shows you the newest video of this channel```"
                        + "```yaml\n" + Main.prefix + "youtube info [YouTube channel id]```"
                        + "```Shows you some information about this channel!```",
                channel, main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil.showUsageTime);
    }

    public void YouTubeNewUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "youtube new [YouTube channel id]`", channel, EmbedsUtil.showUsageTime);
    }

    public void YouTubeInfoUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "youtube info [YouTube channel id]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidYouTubeIdError(TextChannel channel, String id)
    {
        EmbedManager.SendErrorEmbed("`" + id + "` is no valid **YouTubeChannel Id**!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void NoVideosOnChannelInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("The channel has no videos!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendVideoEmbed(TextChannel channel, String url, String thumbnailUrl, String title, String uploaderName, String language, LocalDateTime publishedAt)
    {
        EmbedBuilder builder = new EmbedBuilder();
       DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        builder.setAuthor("YouTube");
        builder.setTitle(uploaderName);
        builder.setDescription("[**" + title + "**](" + url + ")");
        builder.addField("UploadDate", dateFormatter.format(publishedAt), true);
        builder.addField("UploadTime", timeFormatter.format(publishedAt), true);
        builder.addField("Language", language, true);

        builder.setColor(Color.RED);

        builder.setImage(thumbnailUrl);

        //Message
        channel.sendMessage(builder.build()).queue();
    }

    public void SendChannelInfoEmbed(TextChannel channel, String channelUrl, String thumbnailUrl, String channelName, String language, LocalDateTime creationDate, long viewCount, long subscriberCount, long videoCount)
    {
        EmbedBuilder builder = new EmbedBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        builder.setAuthor("YouTube");
        builder.setDescription("[**" + channelName + "**](" + channelUrl + ")");
        builder.addField("CreationDate", dateFormatter.format(creationDate), true);
        builder.addField("Language", language, true);
        builder.addField("Views", Long.toString(viewCount), true);
        builder.addField("Subscriber", Long.toString(subscriberCount), true);
        builder.addField("Videos", Long.toString(videoCount), true);

        builder.setColor(Color.RED);

        builder.setThumbnail(thumbnailUrl);

        //Message
        channel.sendMessage(builder.build()).queue();
    }
}
