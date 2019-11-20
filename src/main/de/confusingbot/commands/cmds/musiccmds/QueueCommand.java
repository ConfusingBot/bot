package main.de.confusingbot.commands.cmds.musiccmds;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import main.de.confusingbot.music.Queue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class QueueCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

        Queue queue = controller.getQueue();
        List<AudioTrack> tracks = queue.getQueueList();

        if (args.length == 1) {
            if (tracks.size() > 0) {
                String queueString = "";
                for (AudioTrack track : tracks) {
                    String url = track.getInfo().uri;
                    String title = track.getInfo().title;
                    String author = track.getInfo().author;
                    queueString += "\uD83C\uDFB5 **[" + title + "](" + url + ")** " + author + "\n";
                }
//TODO description can't be longer than 2048 characters !
                EmbedManager.SendCustomEmbed("Music Queue\uD83D\uDC7E", queueString, Color.decode("#d400ff"), channel, 15);
            } else {
                EmbedManager.SendCustomEmbed("Music Queue\uD83D\uDC7E", "No existing MusicQueue\uD83C\uDFB6", Color.decode("#d400ff"), channel, 5);
                EmbedBuilder builder = new EmbedBuilder();
            }

        } else if (args.length >= 2) {
            switch (args[1]) {
                case "clear":
                    queue.getQueueList().clear();
                    EmbedManager.SendCustomEmbed("Music Queue Cleared\uD83D\uDDD1", "You cleared the Music Queue", Color.decode("#d400ff"), channel, 5);
                    break;
                case "delete":
                    if (args[2] != null) {
                        int index = Integer.parseInt(args[2]) - 1;//because the user won't start counting by 0
                        List<AudioTrack> queueList = queue.getQueueList();
                        if (index < queueList.size() && index > 0) {
                            EmbedManager.SendCustomEmbed("Tracl deleted at " + index,
                                    "You deleted *" + queueList.get(index).getInfo().title + "* from the queue!", Color.decode("#d400ff"), channel, 5);
                            queue.DeleteAtIndex(index);
                        } else {
                            EmbedManager.SendErrorEmbed("Error", "Couldn't delete Track at " + index, channel, 5);
                        }

                    } else {
                        EmbedManager.SendInfoEmbed("Usage", "`" + Main.prefix + "queue delete [position]`", channel, 5);
                    }
                    break;
                default:
                    EmbedManager.SendInfoEmbed("Usage", "`" + Main.prefix + "queue delete [position]`\n`" + Main.prefix + "queue clear`", channel, 5);
                    break;
            }
        }
    }
}
