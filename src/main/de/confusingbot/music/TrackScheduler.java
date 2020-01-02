package main.de.confusingbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.queue.Queue;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.managers.AudioManager;


public class TrackScheduler extends AudioEventAdapter
{

    @Override
    public void onPlayerPause(AudioPlayer player)
    {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        AudioTrack track = player.getPlayingTrack();

        String name = track.getInfo().title;
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

        //Message
        controller.getMusicEmbedManager().getMusicEmbed().PauseTrackEmbed(name, isStream, seconds, minutes, hours, maxSeconds, maxMinutes, maxHours);
    }

    @Override
    public void onPlayerResume(AudioPlayer player)
    {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);

        //Message
        controller.getMusicEmbedManager().DeletePauseSongEmbed();
        controller.getMusicEmbedManager().getMusicEmbed().ResumeTrackEmbed();
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track)
    {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);

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

        //Message
        controller.getMusicEmbedManager().getMusicEmbed().TrackInformationEmbed(author, title, url, isStream, seconds, minutes, hours);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        long guildid = Music.playerManager.getGuildByPlayerHash(player.hashCode());
        MusicController controller = Music.playerManager.getController(guildid);
        Guild guild = controller.getGuild();

        controller.getMusicEmbedManager().DeleteLastSongEmbed();

        Queue queue = controller.getQueue();
        if (endReason.mayStartNext)
        {
            if (queue.hasNext()) return;

            //Message
            controller.getMusicEmbedManager().getMusicEmbed().QueueEndedEmbed();
        }
        else if (endReason.name().equals("REPLACED"))
        {
            //skip command
            return;
        }

        //End Music
        AudioManager manager = guild.getAudioManager();
        player.stopTrack();
        manager.closeAudioConnection();
    }
}
