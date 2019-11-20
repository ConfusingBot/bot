package main.de.confusingbot.commands.cmds.musiccmds;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import main.de.confusingbot.music.Queue;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class SkipCommand implements ServerCommand {
    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

        Queue queue = controller.getQueue();

        if (queue.hasNext()) {
            EmbedManager.SendCustomEmbed("Skipped Song\uD83D\uDD0A", queue.getQueueList().get(0).getInfo().title, Color.decode("#d400ff"), channel, 3);
        } else {
            EmbedManager.SendErrorEmbed("No Song found!\uD83D\uDD07", "There is no other song in the queue", channel, 3);
        }
    }
}
