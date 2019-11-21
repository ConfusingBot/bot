package main.de.confusingbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class AudioLoadResult implements AudioLoadResultHandler {

    private final String uri;
    private final MusicController controller;
    private TextChannel channel;

    public AudioLoadResult(String uri, MusicController controller, TextChannel channel) {
        this.controller = controller;
        this.uri = uri;
        this.channel = channel;
    }

    @Override
    public void trackLoaded(AudioTrack audioTrack) {

        Queue queue = controller.getQueue();
        if (queue.getQueueList().isEmpty() && controller.getPlayer().getPlayingTrack() != null) {
            controller.getPlayer().playTrack(audioTrack);
        } else {
            queue.addTrackToQueue(audioTrack);
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#d400ff"));
        builder.setDescription("You added " + audioTrack.getInfo().title + "️ to the queue\uD83D\uDDC2");

        controller.getEmbeds().sendEmbed(builder, 5);

    }

    @Override
    public void playlistLoaded(AudioPlaylist audioPlaylist) {
        Queue queue = controller.getQueue();

        if (uri.startsWith("ytsearch: ")) {
            queue.addTrackToQueue(audioPlaylist.getTracks().get(0));
            return;
        }

        int added = 0;
        for (AudioTrack track : audioPlaylist.getTracks()) {
            queue.addTrackToQueue((track));
            added++;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#d400ff"));
        builder.setDescription("You added " + added + " tracks\uD83C\uDFB6️ to the queue\uD83D\uDDC2");

        controller.getEmbeds().sendEmbed(builder, 5);
    }

    @Override
    public void noMatches() {

        EmbedManager.SendErrorEmbed("Error", "`Couldn't Find`" + uri, channel, 3);

    }

    @Override
    public void loadFailed(FriendlyException e) {

        EmbedManager.SendErrorEmbed("Error", "`Couldn't Load`" + uri, channel, 3);

    }
}
