package main.de.confusingbot.commands.cmds.musiccmds.playcommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public PlayCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (args.length >= 2)
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

                    if (botVoiceChannel == null || (botVoiceChannel.getIdLong() == voiceChannel.getIdLong()))
                    {
                        //SQL
                        controller.updateChannel(channel, member);

                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) stringBuilder.append(args[i] + " ");

                        String url = stringBuilder.toString().trim();
                        //If url is no Link search this in ytsearch
                        if (!url.startsWith("http"))
                        {
                            url = "ytsearch: " + url;
                        }

                        Connect(voiceChannel);

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

    private void Connect(VoiceChannel voiceChannel)
    {
        AudioManager manager = voiceChannel.getGuild().getAudioManager();
        if (!manager.isConnected())
            manager.openAudioConnection(voiceChannel);

    }
}
