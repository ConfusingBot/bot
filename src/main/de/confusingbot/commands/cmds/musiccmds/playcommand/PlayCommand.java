package main.de.confusingbot.commands.cmds.musiccmds.playcommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length > 1)
        {

            GuildVoiceState state = member.getVoiceState();
            if (state != null)
            {
                VoiceChannel voiceChannel = state.getChannel();
                if (voiceChannel != null)
                {
                    Music.channelID = voiceChannel.getIdLong();
                    MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                    AudioPlayerManager audioPlayerManager = Music.audioPlayerManager;
                    AudioManager manager = voiceChannel.getGuild().getAudioManager();
                    VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                    if (botVoiceChannel.getIdLong() == voiceChannel.getIdLong())
                    {
                        //Connect if bot is in no VoiceChannel
                        if (!manager.isConnected())
                            manager.openAudioConnection(voiceChannel);

                        //SQL
                        MusicUtil.updateChannel(channel, member);

                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) stringBuilder.append(args[i] + " ");

                        String url = stringBuilder.toString().trim();
                        //If url is no Link search this in ytsearch
                        if (!url.startsWith("http"))
                        {
                            url = "ytsearch: " + url;
                        }

                        //Try to Play Song
                        audioPlayerManager.loadItem(url, new AudioLoadResult(url, controller, channel));
                    }
                    else
                    {
                        //Error
                        EmbedsUtil.BotNotInYourVoiceChannelError(channel);
                    }
                }
                else
                {
                    //Information
                    EmbedsUtil.YouAreNotInAVoiceChannelInformation(channel);
                }
            }
        }
        else
        {
            //Usage
            embeds.PlayUsage(channel);
        }

    }
}
