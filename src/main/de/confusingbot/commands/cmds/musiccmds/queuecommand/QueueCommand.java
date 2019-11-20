package main.de.confusingbot.commands.cmds.musiccmds.queuecommand;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import main.de.confusingbot.music.Queue;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class QueueCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());
        Queue queue = controller.getQueue();

        if (args.length == 1)
        {
            //List
            ListQueueCommand(channel, args, queue);
        }
        else if (args.length >= 2)
        {
            switch (args[1])
            {
                case "clear":
                    //Clear
                    ClearQueueCommand(channel, args, queue);
                    break;
                case "delete":
                    //Delete
                    DeleteAtIndexCommand(channel, args, queue);
                    break;
                default:
                    //Usage
                    embeds.GeneralUsage(channel);
                    break;
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListQueueCommand(TextChannel channel, String[] args, Queue queue)
    {
        if (args.length == 2)
        {
            List<AudioTrack> tracks = queue.getQueueList();
            if (tracks.size() > 0)
            {
                String queueString = createQueueString(tracks);

                //TODO description can't be longer than 2048 characters !
                //Message
                embeds.SendMusicQueueEmbed(channel, queueString);
            }
            else
            {
                //Message
                embeds.NoExistingMusicQueueEmbed(channel);
            }
        }
        else
        {
            //Usage
            embeds.ListQueueUsage(channel);
        }
    }

    private void ClearQueueCommand(TextChannel channel, String[] args, Queue queue)
    {
        if (args.length == 2)
        {
            //Clear Queue
            queue.getQueueList().clear();

            //Message
            embeds.SuccessfullyClearedMusicQueue(channel);
        }
        else
        {
            //Usage
            embeds.ClearQueueUsage(channel);
        }
    }

    private void DeleteAtIndexCommand(TextChannel channel, String[] args, Queue queue)
    {
        if (args.length == 3)
        {
            if (args[2] != null)
            {
                int index = Integer.parseInt(args[2]) - 1;//because the user won't start counting by 0
                List<AudioTrack> queueList = queue.getQueueList();
                if (index < queueList.size() && index > 0)
                {
                    //Message
                    embeds.SuccessfullyDeletedTrackAtIndex(channel, index, queueList.get(index).getInfo().title);

                    queue.DeleteAtIndex(index);
                }
                else
                {
                    //Error
                    embeds.CouldNotDeleteTrackAtIndex(channel, index);
                }
            }
            else
            {
                //Usage
                embeds.DeleteAtIndexUsage(channel);
            }
        }
        else
        {
            //Usage
            embeds.DeleteAtIndexUsage(channel);
        }

    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private String createQueueString(List<AudioTrack> tracks)
    {
        String queueString = "";
        for (AudioTrack track : tracks)
        {
            String url = track.getInfo().uri;
            String title = track.getInfo().title;
            String author = track.getInfo().author;
            queueString += "\uD83C\uDFB5 **[" + title + "](" + url + ")** " + author + "\n";
        }

        return queueString;
    }
}
