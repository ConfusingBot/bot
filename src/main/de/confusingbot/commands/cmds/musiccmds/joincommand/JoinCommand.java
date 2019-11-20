package main.de.confusingbot.commands.cmds.musiccmds.joincommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import main.de.confusingbot.music.MusicUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class JoinCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length == 1)
        {
            GuildVoiceState state = member.getVoiceState();

            if (state != null)
            {
                VoiceChannel voiceChannel = state.getChannel();
                if (voiceChannel != null)
                {
                    MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                    Music.channelID = voiceChannel.getIdLong();

                    Connect(voiceChannel);

                    //SQL
                    MusicUtil.updateChannel(channel, member);

                    List<AudioTrack> queue = controller.getQueue().getQueueList();
                    if (queue.size() > 0)
                    {
                        //PlayTrack
                        controller.getPlayer().playTrack(queue.get(0));
                    }
                    else
                    {
                        //Information
                        embeds.YourQueueIsEmptyInformation(channel);
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
            embeds.JoinUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void Connect(VoiceChannel voiceChannel)
    {
        AudioManager manager = voiceChannel.getGuild().getAudioManager();
        if (!manager.isConnected())
            manager.openAudioConnection(voiceChannel);

    }
}
