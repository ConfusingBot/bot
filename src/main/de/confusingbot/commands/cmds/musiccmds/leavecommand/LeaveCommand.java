package main.de.confusingbot.commands.cmds.musiccmds.leavecommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public LeaveCommand()
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
                        AudioPlayer player = controller.getPlayer();
                        VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                        if (botVoiceChannel != null)
                        {
                            if (botVoiceChannel.getIdLong() == voiceChannel.getIdLong())
                            {
                                //SQL
                                controller.updateChannel(channel, member);

                                //StopMusic
                                player.stopTrack();
                                manager.closeAudioConnection();

                                //Message
                                embeds.StoppedMusicMessage(channel);
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
                embeds.LeaveUsage(channel);
            }
        }
    }
}
