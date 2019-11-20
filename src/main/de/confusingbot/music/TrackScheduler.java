package main.de.confusingbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;


public class TrackScheduler extends AudioEventAdapter {


    @Override
    public void onPlayerPause(AudioPlayer player) {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        controller.getEmbeds().SendPauseSongEmbed(player.getPlayingTrack());

    }

    @Override
    public void onPlayerResume(AudioPlayer player) {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        controller.getEmbeds().DeletePauseSongEmbed();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        controller.getEmbeds().SendSongEmbed(track);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        Guild guild = controller.getGuild();

        controller.getEmbeds().DeleteLastSongEmbed();

        if (endReason.mayStartNext) {
            Queue queue = controller.getQueue();

            if (queue.hasNext()) return;

            //TODO inform about, that the que ended
        } else if (endReason.name().equals("REPLACED")) {//skip command
            return;
        }

        AudioManager manager = guild.getAudioManager();
        player.stopTrack();
        manager.closeAudioConnection();

    }
}
