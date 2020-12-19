package de.confusingbot.commands.cmds.musiccmds.trackinfocommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.music.manage.Music;
import de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class TrackInfoCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public TrackInfoCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        // - trackinfo
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length == 1)
            {
                MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());
                AudioManager manager = channel.getGuild().getAudioManager();

                AudioPlayer player = controller.getPlayer();

                AudioTrack track = player.getPlayingTrack();
                if (track != null && manager.isConnected())
                {
                    //Get Data
                    AudioTrackInfo info = track.getInfo();
                    String author = info.author;
                    String title = info.title;
                    String url = info.uri;
                    boolean isStream = info.isStream;
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
                    embeds.TrackInfoEmbed(channel, author, title, url, hours, minutes, seconds, maxHours, maxMinutes, maxSeconds, isStream);
                }
                else
                {
                    //Information
                    embeds.NoSongIsPlaying(channel);
                }
            }
            else
            {
                //Usage
                embeds.TrackInfoUsage(channel);
            }
        }
    }
}
