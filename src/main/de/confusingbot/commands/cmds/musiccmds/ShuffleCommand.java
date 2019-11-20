package main.de.confusingbot.commands.cmds.musiccmds;

import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class ShuffleCommand implements ServerCommand {
    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        message.delete().queue();
        GuildVoiceState state = member.getVoiceState();
        if (state != null) {

            VoiceChannel voiceChannel = state.getChannel();
            if (voiceChannel != null) {
                MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());

                controller.getQueue().Shuffle();

                EmbedManager.SendCustomEmbed("Shuffled", "Shuffled playlist\uD83D\uDD79", Color.decode("#d400ff"), channel, 5);
            }
        }

    }
}
