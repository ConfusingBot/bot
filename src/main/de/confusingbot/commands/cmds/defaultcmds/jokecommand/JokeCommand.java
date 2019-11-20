package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JokeCommand implements ServerCommand
{

    Strings strings = new Strings();
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
            strings.JokeUsage(channel);
        }

    }


}
