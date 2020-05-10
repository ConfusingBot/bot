package main.de.confusingbot.music;

import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.manage.MusicEmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MusicEmbeds
{

    MusicEmbedManager musicEmbedManager;
    Color musicColor = Color.decode("#d400ff");

    public MusicEmbeds(MusicEmbedManager musicEmbedManager)
    {
        this.musicEmbedManager = musicEmbedManager;
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void PauseTrackEmbed(String title, boolean isStream, long seconds, long minutes, long hours, long maxSeconds, long maxMinutes, long maxHours)
    {
        TextChannel channel = musicEmbedManager.getLastUsedChannel();

        String time = ((hours > 0) ? hours + "h " : "") + minutes + "min " + seconds + "s | " + ((maxHours > 0) ? maxHours + "h " : "") + maxMinutes + "min " + maxSeconds + "s";

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(musicColor);
        builder.setTitle("**Paused Song🔈**");
        builder.setDescription("" + title + "\n" + (isStream ? "\uD83D\uDD34 STREAM" : "⏳ " + time));
        builder.setFooter("/pause for unpausing!");

        //Message
        long pauseLastMessageID = EmbedManager.SendEmbedGetMessageID(builder, channel);

        musicEmbedManager.setPauseLastMessageID(pauseLastMessageID);
    }

    public void ResumeTrackEmbed()
    {
        TextChannel channel = musicEmbedManager.getLastUsedChannel();
        EmbedManager.SendCustomEmbed("**Resumed\uD83D\uDD0A**", "Let's go\uD83C\uDFB6", musicColor, channel, 3);
    }

    public void TrackInformationEmbed(String author, String title, String url, boolean isStream, long seconds, long minutes, long hours)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(musicColor);

        //Title
        builder.setTitle("**Currently playing**📡");

        //Description
        if (title.length() > 45)
        {
            title = (title.substring(0, 45) + "...");
        }
        builder.setDescription("[" + title + "](" + url + ")\n" + author);

        String time = ((hours > 0) ? hours + "h " : "") + minutes + "min " + seconds + "s";
        builder.addField("TrackLength⏱", isStream ? "\uD83D\uDD34STREAM" : time, true);

        if (url.startsWith("https://www.youtube.com/watch?v="))
        {
            String videoID = url.replace("https://www.youtube.com/watch?v=", "");
            String imageID = "https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg";

            TextChannel channel = musicEmbedManager.getLastUsedChannel();
            if (channel != null)
            {
                try
                {
                    InputStream file;
                    file = new URL(imageID).openStream();
                    builder.setImage("attachment://thumbnail.png");

                    //Message
                    long musicLastPlayMessageID = channel.sendFile(file, "thumbnail.png").embed(builder.build()).complete().getIdLong();
                    musicEmbedManager.setMusicLastPlayMessageID(musicLastPlayMessageID);

                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            else
            {

                //Message
                long musicLastPlayMessageID = EmbedManager.SendEmbedGetMessageID(builder, channel);
                musicEmbedManager.setMusicLastPlayMessageID(musicLastPlayMessageID);
            }
        }
    }

    public void YouAddedXTracksToQueueEmbed(int added)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(musicColor);
        builder.setDescription("You added **" + added + " tracks\uD83C\uDFB6️** to the queue\uD83D\uDDC2");

        musicEmbedManager.sendMusicEmbed(builder, 5);
    }

    public void YouAddedSongToQueueEmbed(String title)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#d400ff"));
        builder.setDescription("You added **" + title + "️** to the queue\uD83D\uDDC2");

        musicEmbedManager.sendMusicEmbed(builder, 5);
    }

    public void QueueEndedEmbed()
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(musicColor);
        builder.setDescription("Mhh.. I have no music to play!");

        musicEmbedManager.sendMusicEmbed(builder, 5);
    }

    public void NoUserAnymoreEmbed()
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(musicColor);
        builder.setDescription("It looks like nobody want to listen to me..");

        musicEmbedManager.sendMusicEmbed(builder, 5);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoMatchesError(TextChannel channel, String uri)
    {
        EmbedManager.SendErrorEmbed("`Couldn't Find`" + uri, channel, 3);
    }

    public void LoadFailedError(TextChannel channel, String uri)
    {
        EmbedManager.SendErrorEmbed("`Couldn't Load`" + uri, channel, 3);
    }

}