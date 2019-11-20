package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.List;

public class PrivateHelpCommand implements PrivateCommand {
    @Override
    public void performPrivateCommand(User user, PrivateChannel channel, Message message) {
        String[] args = CommandsUtil.messageToArgs(message);

        sendEmbed(HelpManager.getHelp(args), user);
    }

    private void sendEmbed(EmbedBuilder builder, User user){
       user.openPrivateChannel().queue((privateChannel) -> {
           privateChannel.sendMessage(builder.build()).queue();
        });
    }
}
