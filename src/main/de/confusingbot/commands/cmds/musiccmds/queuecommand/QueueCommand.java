package main.de.confusingbot.commands.cmds.musiccmds.queuecommand;

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

import java.util.ArrayList;
import java.util.List;

public class QueueCommand implements ServerCommand
{
    Embeds embeds = new Embeds();

    public QueueCommand()
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
                        ClearQueueCommand(channel, args, queue, member);
                        break;

                    case "delete":
                        //Delete
                        DeleteAtIndexCommand(channel, args, queue, member);
                        break;

                    case "shuffle":
                        //Shuffle
                        ShuffleQueueCommand(channel, args, member);
                        break;

                    default:
                        //Usage
                        embeds.GeneralUsage(channel);
                        break;
                }
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListQueueCommand(TextChannel channel, String[] args, Queue queue)
    {
        List<AudioTrack> tracks = queue.getQueueList();
        if (tracks.size() > 0)
        {
            int maxQueueListLength = 10;
            boolean queueToLarge = tracks.size() >= maxQueueListLength;

            //Get Shorted List of Tracks
            List<AudioTrack> shortTracks = new ArrayList<>();
            for (int i = 0; i < tracks.size(); i++)
            {
                if (i < maxQueueListLength)
                    shortTracks.add(tracks.get(i));
                else
                    break;
            }
            List<String> queueStrings = createQueueString(shortTracks);


            for (int i = 0; i < queueStrings.size(); i++)
            {
                //Message
                embeds.SendMusicQueueEmbed(channel, queueStrings.get(i), i == 0, i == (queueStrings.size() - 1), queueToLarge, tracks.size() - maxQueueListLength);
            }
        }
        else
        {
            //Message
            embeds.NoExistingMusicQueueEmbed(channel);
        }
    }

    private void ShuffleQueueCommand(TextChannel channel, String[] args, Member member)
    {
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
                        if (controller.getQueue().hasNext())
                        {
                            //Shuffle
                            controller.getQueue().Shuffle();

                            //Message
                            embeds.SuccessfullyShuffledQueue(channel);
                        }
                        else
                        {
                            embeds.NoSongInQueueInformation(channel);
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
            embeds.ShuffleUsage(channel);
        }
    }

    private void ClearQueueCommand(TextChannel channel, String[] args, Queue queue, Member member)
    {
        if (args.length == 2)
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
                        //Clear Queue
                        queue.getQueueList().clear();

                        //Message
                        embeds.SuccessfullyClearedMusicQueue(channel);
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
            embeds.ClearQueueUsage(channel);
        }
    }

    private void DeleteAtIndexCommand(TextChannel channel, String[] args, Queue queue, Member member)
    {
        if (args.length == 3)
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
                        if (args[2] != null)
                        {
                            if (CommandsUtil.isNumeric(args[2]))
                            {
                                int index = Integer.parseInt(args[2]) - 1;//because the user won't start counting by 0
                                List<AudioTrack> queueList = queue.getQueueList();
                                if (index <= queueList.size() && index >= 0)
                                {
                                    //Message
                                    embeds.SuccessfullyDeletedTrackAtIndex(channel, index + 1, queueList.get(index).getInfo().title);

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
                                main.de.confusingbot.commands.help.EmbedsUtil.NoNumberError(channel, args[2]);
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
            embeds.DeleteAtIndexUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<String> createQueueString(List<AudioTrack> tracks)
    {
        List<String> queueString = new ArrayList<>();
        int maxQueueCharLength = 1500;
        int messageCounter = 0;

        queueString.add("");

        for (AudioTrack track : tracks)
        {
            //Reset queueString
            if (queueString.get(messageCounter).length() >= maxQueueCharLength)
            {
                queueString.add("");
                messageCounter++;
            }

            //Create QueueString
            String url = track.getInfo().uri;
            String title = track.getInfo().title;
            String author = track.getInfo().author;
            queueString.set(messageCounter,
                    queueString.get(messageCounter) + "\uD83C\uDFB5 **[" + title + "](" + url + ")** " + author + "\n");
        }

        return queueString;
    }
}
