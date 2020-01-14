package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.PrivateCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;

public class PrivateHelpCommand implements PrivateCommand
{
    @Override
    public void performPrivateCommand(User user, PrivateChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);

        sendEmbed(HelpManager.getHelpBuilder(args), user);
    }

    private void sendEmbed(ArrayList<EmbedBuilder> builders, User user)
    {
        user.openPrivateChannel().queue((privateChannel) -> {
            for (EmbedBuilder builder : builders)
                privateChannel.sendMessage(builder.build()).queue();
        });
    }
}
