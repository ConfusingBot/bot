package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.ArrayList;

public interface JokeCategory
{
    public void performJoke(TextChannel channel, JokeManager manager);
}
