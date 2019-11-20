package main.de.confusingbot.music;

import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class UpdateVoiceChannel {

    public static void onPlayerLeave(VoiceChannel channel) {

        if (channel.getMembers().size() <= 1 && channel.getIdLong() == Music.channelID) {

            AudioManager manager = channel.getGuild().getAudioManager();
            manager.closeAudioConnection();

        }

    }
}
