package main.de.confusingbot.commands.cmds.musiccmds;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class LeaveCommand implements ServerCommand {
    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        message.delete().queue();

        GuildVoiceState state = member.getVoiceState();
        if (state != null) {

            VoiceChannel voiceChannel = state.getChannel();
            if (voiceChannel != null) {

                MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                AudioManager manager = voiceChannel.getGuild().getAudioManager();
                AudioPlayer player = controller.getPlayer();

                MusicUtil.updateChannel(channel, member);

                player.stopTrack();
                manager.closeAudioConnection();

                EmbedManager.SendCustomEmbed("Stopped Music\uD83C\uDFB6", "", Color.decode("#d400ff"), channel, 5);

            }
        }

    }
}
