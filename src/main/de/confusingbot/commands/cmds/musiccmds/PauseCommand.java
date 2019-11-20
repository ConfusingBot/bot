package main.de.confusingbot.commands.cmds.musiccmds;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.music.MusicController;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class PauseCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if(args.length == 1){
            MusicController controller = Music.playerManager.getController(channel.getGuild().getIdLong());

            if (!controller.getPaused()) {
                controller.setPaused(true);
                controller.getPlayer().setPaused(controller.getPaused());
            } else {
                controller.setPaused(false);
                controller.getPlayer().setPaused(controller.getPaused());

                EmbedManager.SendCustomEmbed("**Resumed\uD83D\uDD0A**", "Let's go\uD83C\uDFB6", Color.decode("#d400ff"), channel, 3);
            }
        }else{
            EmbedManager.SendInfoEmbed("Usage", "`" + Main.prefix + "pause`", channel, 5);
        }


    }
}
