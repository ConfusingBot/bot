package main.de.confusingbot.commands.cmds.musiccmds.playcommand;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.*;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public PlayCommand()
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
            if (args.length >= 2)
            {
                GuildVoiceState state = member.getVoiceState();
                if (state != null)
                {
                    VoiceChannel voiceChannel = state.getChannel();
                    if (voiceChannel != null)
                    {
                        MusicController controller = Music.playerManager.getController(voiceChannel.getGuild().getIdLong());
                        long lastMemberId = controller.getLastUsedMemberId();
                        controller.channelID = voiceChannel.getIdLong();
                        AudioPlayerManager audioPlayerManager = Music.audioPlayerManager;
                        AudioManager manager = voiceChannel.getGuild().getAudioManager();
                        VoiceChannel botVoiceChannel = manager.getConnectedChannel();

                        if (botVoiceChannel == null || (botVoiceChannel.getIdLong() == voiceChannel.getIdLong()))
                        {
                            if (voiceChannel.getUserLimit() == 0 || voiceChannel.getUserLimit() > voiceChannel.getMembers().size())
                            {
                                //Clear old queue if new Member want to use the Bot
                                if(member.getIdLong() != lastMemberId) controller.getQueue().getQueueList().clear();

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
                                //Information
                                EmbedsUtil.NoSpaceForBotInformation(channel);
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

    private void Connect(VoiceChannel voiceChannel)
    {
        AudioManager manager = voiceChannel.getGuild().getAudioManager();
        if (!manager.isConnected())
            manager.openAudioConnection(voiceChannel);

    }
}
