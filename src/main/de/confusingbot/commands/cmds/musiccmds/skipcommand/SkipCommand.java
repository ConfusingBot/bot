package main.de.confusingbot.commands.cmds.musiccmds.skipcommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.queue.Queue;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class SkipCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public SkipCommand()
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

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length == 1)
            {
                GuildVoiceState state = member.getVoiceState();
                if (state != null)
                {
                    VoiceChannel voiceChannel = state.getChannel();
                    if (voiceChannel != null)
                    {
                        AudioManager manager = voiceChannel.getGuild().getAudioManager();
                        VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                        if (voiceChannel.getIdLong() == botVoiceChannel.getIdLong())
                        {
                            MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

                            Queue queue = controller.getQueue();
                            AudioTrack lastPlayingTrack = controller.getPlayer().getPlayingTrack();

                            //Unpause song if song is paused
                            if (controller.getPlayer().isPaused())
                            {
                                controller.getPlayer().setPaused(false);
                            }

                            if (queue.hasNext())
                            {
                                //Message
                                embeds.SuccessfullySkippedTrack(channel, lastPlayingTrack.getInfo().title);
                            }
                            else
                            {
                                //Information
                                embeds.NoOtherSongInQueueInformation(channel);
                            }
                        }
                        else
                        {
                            EmbedsUtil.BotNotInYourVoiceChannelError(channel);
                        }
                    }
                    else
                    {
                        EmbedsUtil.YouAreNotInAVoiceChannelInformation(channel);
                    }
                }
            }
            else
            {
                //Usage
                embeds.SkipUsage(channel);
            }
        }
    }
}
