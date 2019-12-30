package main.de.confusingbot.music;

import main.de.confusingbot.Main;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class UpdateVoiceChannel
{

    public static void onPlayerLeave(VoiceChannel channel)
    {
        if (channel.getMembers().size() <= 1 && channel.getIdLong() == Music.channelID)
        {
            MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

            controller.getPlayer().setPaused(false);
            controller.getPlayer().stopTrack();
            AudioManager manager = channel.getGuild().getAudioManager();
            manager.closeAudioConnection();
        }
    }
}
