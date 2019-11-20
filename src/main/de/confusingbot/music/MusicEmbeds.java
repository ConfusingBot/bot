package main.de.confusingbot.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class MusicEmbeds {

    MusicController controller;
    Guild guild;

    public MusicEmbeds(MusicController controller) {
        this.controller = controller;
        this.guild = controller.getGuild();
    }

    private long musicLastPlayMessageID = -1;
    private long pauseLastMessageID = -1;

    public void SendSongEmbed(AudioTrack track) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#d400ff"));
        AudioTrackInfo info = track.getInfo();
        String author = info.author;
        String title = info.title;
        String url = info.uri;
        boolean isStream = info.isStream;
        long length = track.getDuration();

        long seconds = length / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        minutes %= 60;
        seconds %= 60;

        builder.setTitle("**Currently playing**📡");
        builder.setDescription("[" + title + "](" + url + ")\n" + author);

        String time = ((hours > 0) ? hours + "h " : "") + minutes + "min " + seconds + "s";
        builder.addField("TrackLength⏱", isStream ? "\uD83D\uDD34STREAM" : time, true);

        TextChannel channel = getLastUsedChannel();
        if (url.startsWith("https://www.youtube.com/watch?v=")) {
            String videoID = url.replace("https://www.youtube.com/watch?v=", "");
            String imageID = "https://img.youtube.com/vi/" + videoID + "/hqdefault.jpg";


            try {
                InputStream file;
                file = new URL(imageID).openStream();
                builder.setImage("attachment://thumbnail.png");

                if (channel != null) {
                    channel.sendTyping();
                    musicLastPlayMessageID = channel.sendFile(file, "thumbnail.png").embed(builder.build()).complete().getIdLong();
                }

            } catch (IOException e) {
                EmbedManager.SendErrorEmbed("Failed to load Image", "Failed to load " + imageID, channel, 5);
            }
        } else {
            if (channel != null) musicLastPlayMessageID = channel.sendMessage(builder.build()).complete().getIdLong();
        }

    }

    public void DeleteLastSongEmbed() {
        try {
            if (musicLastPlayMessageID != -1) {

                TextChannel channel = getLastUsedChannel();

                channel.deleteMessageById(musicLastPlayMessageID).queue();

            }
        } catch (Exception e) {
            System.err.println("Couldn't delete LastSongEmbed");
        }

    }

    public void SendPauseSongEmbed(AudioTrack track) {
        TextChannel channel = getLastUsedChannel();

        boolean isStream = track.getInfo().isStream;
        long position = track.getPosition();
        long length = track.getDuration();

        long seconds = position / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        seconds %= 60;
        minutes %= 60;

        long maxSeconds = length / 1000;
        long maxMinutes = maxSeconds / 60;
        long maxHours = maxMinutes / 60;
        maxSeconds %= 60;
        maxMinutes %= 60;

        String time = ((hours > 0) ? hours + "h " : "") + minutes + "min " + seconds + "s | " + ((maxHours > 0) ? maxHours + "h " : "") + maxMinutes + "min " + maxSeconds + "s";


        pauseLastMessageID = EmbedManager.SendCustomEmbedGetMessageID("**Paused Song\uD83D\uDD08**",
                "" + track.getInfo().title + "\n" + (isStream ? "\uD83D\uDD34 STREAM" : "⏳ " + time),
                Color.decode("#d400ff"), channel);
    }

    public void DeletePauseSongEmbed() {
        try {
            if (pauseLastMessageID != -1) {

                TextChannel channel = getLastUsedChannel();

                channel.deleteMessageById(pauseLastMessageID).queue();
            }
        } catch (Exception e) {
            System.err.println("Couldn't delete LastSongEmbed");
        }
    }

    private TextChannel getLastUsedChannel() {
        return guild.getTextChannelById(controller.getLastUsedChannelId());
    }

    public void sendEmbed(EmbedBuilder builder, int time) {

        TextChannel channel = getLastUsedChannel();

        if (guild != null) {

            if (channel != null) {
                if (time != 0) {
                    channel.sendMessage(builder.build()).complete().delete().queueAfter(time, TimeUnit.SECONDS);
                } else {
                    channel.sendMessage(builder.build()).queue();
                }
            }
        }

    }
}
