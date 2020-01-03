package main.de.confusingbot.commands.cmds.musiccmds.skipcommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.queue.Queue;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public SkipCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (args.length == 1)
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
            //Usage
            embeds.SkipUsage(channel);
        }
    }
}
