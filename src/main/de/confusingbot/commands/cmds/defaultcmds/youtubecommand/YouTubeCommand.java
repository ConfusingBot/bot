package main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.youtubeapi.YouTubeAPIManager;
import net.dv8tion.jda.api.entities.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class YouTubeCommand implements ServerCommand
{

    Embeds embeds = YouTubeCommandManager.embeds;
    SQL sql = YouTubeCommandManager.sql;

    public YouTubeCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- youtube new [channelID]
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (args.length > 1)
        {
            switch (args[1])
            {
                case "new":
                    NewCommand(channel, args);
                    break;
                case "info":
                    InfoCommand(channel, args);
                    break;
                case "announcement":
                    AnnouncementHandler(message, channel, args);
                    break;
                default:
                    //Usage
                    embeds.GeneralUsage(channel);
                    break;
            }
        }
        else
        {
            //Usage
            embeds.GeneralUsage(channel);
        }
    }

    private void AnnouncementHandler(Message message, TextChannel channel, String[] args)
    {
        //- youtube announcement add [channelID]
        Member member = message.getMember();
        if (member.hasPermission(channel, YouTubeCommandManager.youtubeAnnouncementPermission))
        {
            if (args.length > 2)
            {
                switch (args[2])
                {
                    case "add":
                        YoutubeAnnouncementAddCommand(message, channel, args);
                        break;
                    case "remove":
                        YoutubeAnnouncementRemoveCommand(channel, args);
                        break;
                    case "list":
                        //TODO
                        break;
                    default:
                        embeds.YouTubeAnnouncementsGeneralUsage(channel);
                        break;
                }
            }
            else
            {
                embeds.YouTubeAnnouncementsGeneralUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel, YouTubeCommandManager.youtubeAnnouncementPermission);
        }

    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void YoutubeAnnouncementAddCommand(Message message, TextChannel channel, String[] args)
    {
        Guild guild = message.getGuild();
        if (args.length > 3)
        {
            List<Role> roles = message.getMentionedRoles();
            List<Long> roleids = new ArrayList<>();
            for (Role role : roles) roleids.add(role.getIdLong());
            String roleidsString = !roleids.isEmpty() ? CommandsUtil.codeLong(roleids, ", ") : " ";
            String channelId = args[3];
            String description = ((args.length > 4) ? CommandsUtil.buildWholeString(args, 4, args.length, roles) : " ");

            if (!sql.ExistsInSQL(guild.getIdLong(), channelId))
            {
                //SQL
                sql.addToSQL(guild.getIdLong(), channel.getIdLong(), channelId, description, roleidsString);

                //Message
                embeds.SuccessfulAddedAnnouncement(channel, channelId);
            }
            else
            {
                //Error
                embeds.YouTubeAnnouncementAlreadyExistsError(channel, channelId);
            }
        }
        else
        {
            //Usage
            embeds.YouTubeAnnouncementsAddUsage(channel);
        }
    }

    private void YoutubeAnnouncementRemoveCommand(TextChannel channel, String[] args)
    {
        Guild guild = channel.getGuild();
        if (args.length > 3)
        {
            String channelId = args[3];
            if (sql.ExistsInSQL(guild.getIdLong(), channelId))
            {
                //SQL
                sql.removeFormSQL(guild.getIdLong(), channelId);

                //Message
                embeds.SuccessfulRemovedAnnouncement(channel, channelId);
            }
            else
            {
                //Error
                embeds.YouTubeAnnouncementNotExistsError(channel, channelId);
            }
        }
        else
        {
            //Usage
            embeds.YouTubeAnnouncementsRemoveUsage(channel);
        }
    }

    private void NewCommand(TextChannel channel, String[] args)
    {
        if (args.length == 3)
        {
            String channelID = args[2];
            JSONObject videosObject = YouTubeAPIManager.getVideosOfChannelByChannelId(channelID);
            if (videosObject != null && videosObject.isNull("error"))
            {
                JSONArray itemsObject = videosObject.getJSONArray("items");
                if (itemsObject.length() > 0)
                {
                    JSONObject newVideoObject = null;
                    for (int i = 0; i < itemsObject.length(); i++)
                    {
                        newVideoObject = itemsObject.getJSONObject(i);
                        if (newVideoObject.getJSONObject("id").get("kind").toString().contains("video"))
                            break;
                        else
                            newVideoObject = null;
                    }
                    if (newVideoObject != null)
                    {
                        JSONObject newVideoSnippetObject = newVideoObject.getJSONObject("snippet");

                        String videoId = newVideoObject.getJSONObject("id").getString("videoId");
                        String url = "https://www.youtube.com/watch?v=" + videoId;
                        LocalDateTime publishedAt = CommandsUtil.DateTimeConverter(newVideoSnippetObject.getString("publishedAt"));
                        String thumbnailUrl = newVideoSnippetObject.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                        String title = newVideoSnippetObject.get("title").toString();
                        String uploaderName = newVideoSnippetObject.getString("channelTitle");
                        String language = videosObject.getString("regionCode");

                        //Message
                        embeds.SendVideoEmbed(channel, url, thumbnailUrl, title, uploaderName, language, publishedAt);
                    }
                    else
                    {
                        //Information
                        embeds.NoVideosOnChannelInformation(channel);
                    }
                }
                else
                {
                    //Information
                    embeds.NoVideosOnChannelInformation(channel);
                }
            }
            else
            {
                //Error
                embeds.NoValidYouTubeIdError(channel, channelID);
            }
        }
        else
        {
            //Usage
            embeds.YouTubeNewUsage(channel);
        }

    }

    private void InfoCommand(TextChannel channel, String[] args)
    {
        if (args.length == 3)
        {
            String channelId = args[2];

            JSONObject channelObject = YouTubeAPIManager.getChannelByIDOrName(channelId);
            if (channelObject != null && channelObject.isNull("error"))
            {
                JSONObject channelSnippetObject = channelObject.getJSONObject("snippet");
                JSONObject channelStatisticsObject = channelObject.getJSONObject("statistics");

                String channelUrl = "https://www.youtube.com/channel/" + channelObject.getString("id");
                String channelName = channelSnippetObject.getString("title");
                String thumbnailUrl = channelSnippetObject.getJSONObject("thumbnails").getJSONObject("default").getString("url");
                LocalDateTime creationDate = CommandsUtil.DateTimeConverter(channelSnippetObject.getString("publishedAt"));
                String country = channelSnippetObject.isNull("country") ? "none" : channelSnippetObject.getString("country");

                long viewCount = channelStatisticsObject.getLong("viewCount");
                long subscriberCount = channelStatisticsObject.getLong("subscriberCount");
                long videoCount = channelStatisticsObject.getLong("videoCount");

                //Message
                embeds.SendChannelInfoEmbed(channel, channelUrl, thumbnailUrl, channelName, country, creationDate, viewCount, subscriberCount, videoCount);
            }
            else
            {
                //Error
                embeds.NoValidYouTubeIdError(channel, channelId);
            }
        }
        else
        {
            //Usage
            embeds.YouTubeInfoUsage(channel);
        }
    }
}
