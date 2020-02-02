package main.de.confusingbot.manage.youtubeapi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class YouTubeAPIManager
{
    //https://console.developers.google.com/ -> YouTubeDiscordBot
    public static final String DEVELOPER_KEY = "AIzaSyDrK0FCRPEK1uanvgnvEU1NmifwmBVpex0";

    public static JSONObject getVideosOfChannelByChannelId(String channelId)
    {
        //Example https://www.googleapis.com/youtube/v3/search?key=AIzaSyA_dVJ9HbehznusECr3Q_Puwqs3T1mHcVc&channelId=UCNth2NDhGthv91iC6QHn9DA&part=snippet,id&order=date&maxResults=20
        JSONObject videosObject = new JSONObject();
        try
        {
            videosObject = JsonReader.readJsonFromUrl("https://www.googleapis.com/youtube/v3/search?key=" + DEVELOPER_KEY + "&channelId=" + channelId + "&part=snippet,id&order=date&maxResults=20");
        } catch (IOException | JSONException e)
        {
            videosObject.put("error", "A error happens");
        }

        return videosObject;
    }

    public static JSONObject getVideoByID(String videoId)
    {
        //Example: https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=a5Mjps7PBsQ&key=AIzaSyA_dVJ9HbehznusECr3Q_Puwqs3T1mHcVc
        JSONObject videoObject = new JSONObject();
        try
        {
            videoObject = JsonReader.readJsonFromUrl("https://www.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=" + videoId + "&key=" + DEVELOPER_KEY);
        } catch (IOException | JSONException e)
        {
            videoObject.put("error", "A error happens");
        }

        return videoObject;
    }

    public static JSONObject getChannelByIDOrName(String channelId)
    {
        //Example: https://www.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id=UCNth2NDhGthv91iC6QHn9DA&key=[YOUR_API_KEY]
        //Example2: Does not work perfect :/
        // https://www.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&forUsername=BennoDev&key=[YOUR_API_KEY]

        JSONObject channelObject = new JSONObject();
        try
        {
            channelObject = JsonReader.readJsonFromUrl("https://www.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id=" + channelId + "&key=" + DEVELOPER_KEY);
            channelObject = channelObject.getJSONArray("items").getJSONObject(0);

        } catch (IOException | JSONException e)
        {
            channelObject.put("error", "A error happens");
        }

        return channelObject;
    }
}
