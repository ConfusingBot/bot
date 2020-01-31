package main.de.confusingbot.manage.youtubeapi;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ActivityListResponse;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.VideoListResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class YouTubeAPIManager
{
    public static final String DEVELOPER_KEY = "AIzaSyA_dVJ9HbehznusECr3Q_Puwqs3T1mHcVc";

    public static final String APPLICATION_NAME = "ConfusingBot-YoutubeAPI";
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Build and return an authorized API client service.
     *
     * @return an authorized API client service
     * @throws GeneralSecurityException, IOException
     */
    public static YouTube getService() throws GeneralSecurityException, IOException
    {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    //AktivitätInfo -> UploadedVideos..
    public static ActivityListResponse getResponseById(String channelId)
    {
        ActivityListResponse response = null;
        try
        {
            YouTube youtubeService = getService();
            // Define and execute the API request
            YouTube.Activities.List request = youtubeService.activities()
                    .list("snippet,contentDetails");
            response = request.setKey(DEVELOPER_KEY)
                    .setChannelId(channelId)
                    .execute();
        } catch (GeneralSecurityException | IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    //Video Infos
    public static VideoListResponse getVideoResponseById(String videoId)
    {
        VideoListResponse response = null;
        try
        {
            YouTube youtubeService = getService();
            // Define and execute the API request
            YouTube.Videos.List request = youtubeService.videos()
                    .list("snippet,contentDetails,statistics");
            response = request.setId(videoId).execute();
        } catch (GeneralSecurityException | IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    //ChannelInfos
    public static ChannelListResponse getChannelListResponse(String channelId)
    {
        ChannelListResponse response = null;

        try
        {
            YouTube youtubeService = getService();
            // Define and execute the API request
            YouTube.Channels.List request = youtubeService.channels()
                    .list("snippet,contentDetails,statistics");
            response = request.setKey(DEVELOPER_KEY)
                    .setId(channelId)
                    .execute();
        } catch (GeneralSecurityException | IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }
}
