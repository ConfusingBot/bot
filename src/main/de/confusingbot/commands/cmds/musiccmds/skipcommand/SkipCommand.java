package main.de.confusingbot.commands.cmds.musiccmds.skipcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.music.manage.MusicController;
import main.de.confusingbot.music.queue.Queue;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SkipCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length == 2)
        {
            MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

            Queue queue = controller.getQueue();

            if (queue.hasNext())
            {
                //Message
                embeds.SuccessfullySkippedTrack(channel, queue.getQueueList().get(0).getInfo().title);
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
