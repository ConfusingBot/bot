package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class JokeCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    JokeManager manager = new JokeManager();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- joke [category]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (!manager.perform(args[1], channel))
        {
            //Usage
            embeds.JokeUsage(channel);
        }

    }


}
