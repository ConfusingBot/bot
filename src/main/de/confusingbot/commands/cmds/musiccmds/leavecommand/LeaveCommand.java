package main.de.confusingbot.commands.cmds.musiccmds.leavecommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

import java.awt.*;

public class LeaveCommand implements ServerCommand
{

    Strings strings = new Strings();

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
                MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                AudioManager manager = voiceChannel.getGuild().getAudioManager();
                AudioPlayer player = controller.getPlayer();
                VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                if (botVoiceChannel == null)
                {
                    if (voiceChannel != null && botVoiceChannel.getIdLong() == voiceChannel.getIdLong())
                    {
                        MusicUtil.updateChannel(channel, member);

                        //StopMusic
                        player.stopTrack();
                        manager.closeAudioConnection();

                        //Message
                        strings.StoppedMusicMessage(channel);
                    }
                    else
                    {
                        //Error
                        strings.AreNotInBotVoiceChannelError(channel);
                    }
                }
                else
                {
                    //Error
                    strings.BotIsNotInVoiceChannelERror(channel);
                }
            }
            else
            {
                //Usage
                strings.LeaveUsage(channel);
            }
        }
    }
}
