package main.de.confusingbot.commands.cmds.musiccmds.shufflecommand;

import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.managers.AudioManager;

public class ShuffleCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();
        if (args.length == 2)
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

                    if (voiceChannel.getIdLong() == botVoiceChannel.getIdLong())
                    {
                        //Shuffle
                        controller.getQueue().Shuffle();

                        //Message
                        embeds.SuccessfullyShuffledQueue(channel);
                    }
                    else
                    {
                        EmbedsUtil.BotNotInYourVoiceChannelError(channel);
                    }
                }
            }
        }
        else
        {
            embeds.ShuffleUsage(channel);
        }
    }
}
