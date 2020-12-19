package de.confusingbot.commands.cmds.musiccmds.pausecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.music.manage.Music;
import de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PauseCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public PauseCommand()
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
}
