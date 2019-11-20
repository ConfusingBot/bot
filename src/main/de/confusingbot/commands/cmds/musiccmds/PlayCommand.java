package main.de.confusingbot.commands.cmds.musiccmds;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {
    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length > 1) {

            GuildVoiceState state = member.getVoiceState();
            if (state != null) {

                VoiceChannel voiceChannel = state.getChannel();
                if (voiceChannel != null) {

                    Music.channelID = voiceChannel.getIdLong();
                    MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                    AudioPlayerManager audioPlayerManager = Music.audioPlayerManager;
                    AudioManager manager = voiceChannel.getGuild().getAudioManager();

                    if (!manager.isConnected())
                        manager.openAudioConnection(voiceChannel);

                    MusicUtil.updateChannel(channel, member);

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) stringBuilder.append(args[i] + " ");

                    String url = stringBuilder.toString().trim();
                    if (!url.startsWith("http")) {
                        //if no link
                        url = "ytsearch: " + url;
                    }

                    audioPlayerManager.loadItem(url, new AudioLoadResult(url, controller, channel));

                } else {
                    EmbedManager.SendInfoEmbed("Information", "`Ups, you aren't in a VoiceChannel`\uD83C\uDF99", channel, 5);
                }

            } else {
                EmbedManager.SendInfoEmbed("Information", "`Ups, you aren't in a VoiceChannel\uD83C\uDF99`", channel, 5);
            }

        } else {
            EmbedManager.SendInfoEmbed("Usage", "`" + Main.prefix + "play [songURL / search query]`", channel, 5);
        }

    }
}
