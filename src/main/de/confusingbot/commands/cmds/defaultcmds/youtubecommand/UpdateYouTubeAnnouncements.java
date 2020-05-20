package main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.youtubeapi.YouTubeAPIManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class UpdateYouTubeAnnouncements
{
    public void onSecond()
    {
        //YouTube use the Zone UTC for saving its date!
        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of("UTC"));

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM youtubeannouncement");
            if(set != null)
            {
                while (set.next())
                {
                    //SQL
                    String youtubeChannelID = set.getString("youtubechannelid");
                    long guildID = set.getLong("guildid");
                    long channelID = set.getLong("channelid");

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                    if (guild != null)
                    {
                        TextChannel channel = guild.getTextChannelById(channelID);

                        JSONObject videosObject = YouTubeAPIManager.getVideosOfChannelByChannelId(youtubeChannelID);
                        //If error happens by requesting to YouTubeAPI
                        if (videosObject == null || !videosObject.isNull("error")) return;

                        //If guild or channel doesn't exist anymore
                        if (channel == null || guild == null)
                        {
                            YouTubeCommandManager.sql.removeFormSQL(guildID, youtubeChannelID);
                            return;
                        }

                        JSONArray itemsObject = videosObject.getJSONArray("items");
                        if (itemsObject.length() > 0)
                        {
                            JSONObject newVideoObject = itemsObject.getJSONObject(0);

                            JSONObject newVideoSnippetObject = newVideoObject.getJSONObject("snippet");
                            LocalDateTime publishedAt = CommandsUtil.DateTimeConverter(newVideoSnippetObject.getString("publishedAt"));
                            if (publishedAt.plusMinutes(5).isAfter(currentTime))
                            {
                                //SQL
                                String description = set.getString("description");
                                String roleidsString = set.getString("roleids");
                                String roleMentionedString = getRoleMentionedString(roleidsString, guild, youtubeChannelID);

                                String videoId = newVideoSnippetObject.getJSONObject("resourceId").getString("videoId");
                                String url = "https://www.youtube.com/watch?v=" + videoId;
                                String thumbnailUrl = newVideoSnippetObject.getJSONObject("thumbnails").getJSONObject("high").getString("url");
                                String title = newVideoSnippetObject.get("title").toString();
                                String uploaderName = newVideoSnippetObject.getString("channelTitle");

                                //Message
                                YouTubeCommandManager.embeds.SendYoutubeAnnouncementEmbed(channel, url, thumbnailUrl, title, uploaderName, roleMentionedString, description);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private String getRoleMentionedString(String roleidsString, Guild guild, String youtubeChannelID)
    {
        List<Long> roleIDs = !roleidsString.equals(" ") ? CommandsUtil.encodeLong(roleidsString, ", ") : null;
        if (roleIDs == null || roleIDs.isEmpty()) return " ";
        String roleMentionedString = "";

        for (long roleid : roleIDs)
        {
            Role role = guild.getRoleById(roleid);
            if (role != null)
            {
                roleMentionedString += role.getAsMention() + " ";
            }
            else
            {
                roleIDs.remove(roleid);

                //SQL
                YouTubeCommandManager.sql.UpdateRoleIdsInSQL(guild.getIdLong(), youtubeChannelID, CommandsUtil.codeLong(roleIDs, ", "));
            }
        }

        return roleMentionedString;
    }
}
