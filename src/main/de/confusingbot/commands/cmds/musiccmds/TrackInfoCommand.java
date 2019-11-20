package main.de.confusingbot.commands.cmds.musiccmds;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;

public class TrackInfoCommand implements ServerCommand {


    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        // - trackinfo

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

        AudioPlayer player = controller.getPlayer();

        AudioTrack track = player.getPlayingTrack();
        if (track != null) {
            AudioTrackInfo info = track.getInfo();

            String author = info.author;
            String title = info.title;
            String url = info.uri;
            boolean isStream = info.isStream;
            long position = track.getPosition();
            long length = track.getDuration();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.decode("#d400ff"));
            builder.setAuthor(author);
            builder.setTitle(title, url);

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

            builder.setDescription(isStream ? "\uD83D\uDD34 STREAM" : "⏳ " + time);

            EmbedManager.SendEmbed(builder, channel, 10);
        } else {
            EmbedManager.SendInfoEmbed("Information", "Nothing is playing\uD83D\uDD07", channel, 5);
        }


    }
}
