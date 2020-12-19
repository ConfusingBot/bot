package de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "youtube\n```" +
                " ```Show you some useful stuff from YouTube```\n");

        HelpManager.admin.add("```yaml\n" + Main.prefix + "youtube announcement\n```" +
                " ```Create YouTubeAnnouncements which will inform you if a new video has been uploaded```" +
                "```fix\n" + YouTubeCommandManager.youtubeAnnouncementPermission.name() + "\n```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "youtube new [YouTube channel id]\n```"
                        + "```Shows you the newest video of this channel```"
                        + "```yaml\n" + Main.prefix + "youtube info [YouTube channel id]```"
                        + "```Shows you some information about this channel!```"
                        + "```yaml\n" + Main.prefix + "youtube announcement```"
                        + "```Shows you all YouTubeAnnouncement Commands!```",
                channel, EmbedsUtil.showUsageTime);
    }

    public void YouTubeAnnouncementsGeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("```yaml\n" + Main.prefix + "youtube announcement add [YouTube channel id] ([Description]) ([@roles])\n```"
                        + "```Adds a YouTube announcement!```"
                        + "```yaml\n" + Main.prefix + "youtube announcement remove [YouTube channel id]```"
                        + "```Removes the YouTube announcement!```"
                        + "```yaml\n" + Main.prefix + "youtube announcement list```"
                        + "```List all YouTube announcements of this server```",
                channel, EmbedsUtil.showUsageTime);

    }

    public void YouTubeNewUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "youtube new [YouTube channel id]\n```"
                + "```Shows you the newest video of this channel```", channel, EmbedsUtil.showUsageTime);
    }

    public void YouTubeInfoUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "youtube info [YouTube channel id]```"
                + "```Shows you some information about this channel!```", channel, EmbedsUtil.showUsageTime);
    }

    public void YouTubeAnnouncementsAddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "youtube announcement add [YouTube channel id] ([Description]) ([@roles])\n```"
                + "```Adds a YouTube announcement!```", channel, EmbedsUtil.showUsageTime);
    }

    public void YouTubeAnnouncementsRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "youtube announcement remove [YouTube channel id]```"
                + "```Removes the YouTube announcement!```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoValidYouTubeIdError(TextChannel channel, String id)
    {
        EmbedManager.SendErrorEmbed("`" + id + "` is no valid **YouTubeChannel Id**!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void YouTubeAnnouncementNotExistsError(TextChannel channel, String channelId)
    {
        EmbedManager.SendErrorEmbed("The YouTube announcement with the id " + channelId + " does not exist!", channel, EmbedsUtil.showErrorTime);
    }

    public void YouTubeAnnouncementAlreadyExistsError(TextChannel channel, String channelId)
    {
        EmbedManager.SendErrorEmbed("The YouTube announcement with the id " + channelId + " does already exist!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void NoVideosOnChannelInformation(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("The channel has no videos!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoYoutubeAnnouncements(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("This server has no YouTube Announcements!", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulRemovedAnnouncement(TextChannel channel, String channelId)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully removed " + channelId + " from the YouTubeAnnouncements!", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfulAddedAnnouncement(TextChannel channel, String channelId)
    {
        EmbedManager.SendSuccessEmbed("You sucessfully added " + channelId + " to the YouTubeAnnouncements!", channel, EmbedsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendVideoEmbed(TextChannel channel, String url, String thumbnailUrl, String title, String uploaderName, LocalDateTime publishedAt)
    {
        EmbedBuilder builder = new EmbedBuilder();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        builder.setAuthor("YouTube");
        builder.setTitle(uploaderName);
        builder.setDescription("[**" + title + "**](" + url + ")");
        builder.addField("UploadDate", dateFormatter.format(publishedAt), true);
        builder.addField("UploadTime", timeFormatter.format(publishedAt), true);

        builder.setColor(Color.RED);

        builder.setImage(thumbnailUrl);

        //Message
        EmbedManager.SendEmbed(builder, channel, 0);
    }

    public void SendYoutubeAnnouncementEmbed(TextChannel channel, String url, String thumbnailUrl, String title, String uploaderName, String roleMentionedString, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("YouTube");
        builder.setDescription("[**" + title + "**](" + url + ")\n" + description + "\n\n" + roleMentionedString);
        builder.setFooter(uploaderName);

        builder.setColor(Color.RED);

        builder.setImage(thumbnailUrl);

        //Message
        EmbedManager.SendEmbed(builder, channel, 0);
    }

    public void SendYoutubeAnnouncementsListEmbed(TextChannel channel, String announcementsString)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("\uD83D\uDCC4Announcement List");
        builder.setDescription(announcementsString);
        builder.setColor(Color.RED);

        //Message
        EmbedManager.SendEmbed(builder, channel, 0);
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
        EmbedManager.SendEmbed(builder, channel, 0);
    }
}
