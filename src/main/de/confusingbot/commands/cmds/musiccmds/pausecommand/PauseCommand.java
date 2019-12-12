package main.de.confusingbot.commands.cmds.musiccmds.pausecommand;

import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PauseCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public PauseCommand()
    {
        embeds.HelpEmbed();
    }

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
                    AudioManager manager = voiceChannel.getGuild().getAudioManager();
                    VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                    if (botVoiceChannel != null)
                    {
                        if (botVoiceChannel.getIdLong() == voiceChannel.getIdLong())
                        {
                            if (controller.getPlayer().getPlayingTrack() != null)
                            {
                                if (!controller.getPaused())
                                {
                                    controller.setPaused(true);
                                    controller.getPlayer().setPaused(controller.getPaused());
                                }
                                else
                                {
                                    controller.setPaused(false);
                                    controller.getPlayer().setPaused(controller.getPaused());
                                }
                            }
                            else
                            {
                                //Error
                                embeds.NoPlayingTrackError(channel);
                            }
                        }
                        else
                        {
                            //Error
                            EmbedsUtil.BotNotInYourVoiceChannelError(channel);
                        }
                    }
                    else
                    {
                        //Error
                        EmbedsUtil.BotNotInVoiceChannelError(channel);
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
            embeds.PauseUsage(channel);
        }
    }
}
