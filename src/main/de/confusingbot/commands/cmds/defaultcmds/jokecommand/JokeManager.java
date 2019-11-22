package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories.GeneralJokes;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories.JackNorrisJokes;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories.MotherJokes;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class JokeManager
{
    public ConcurrentHashMap<String, JokeCategory> jokes;

    public JokeManager()
    {
        jokes = new ConcurrentHashMap<>();
        this.jokes.put("jacknorris", new JackNorrisJokes());
        this.jokes.put("mother", new MotherJokes());
        this.jokes.put("", new GeneralJokes());
    }

    public boolean perform(String command, TextChannel channel)
    {
        JokeCategory cat = this.jokes.get(command.toLowerCase());
        if (cat != null)
        {
            cat.performJoke(channel, this);
            return true;
        }
        return false;
    }

    public List<String> outputJokes = new ArrayList<>();
    public void GenerateJokeEmbed(String title, List<String> jokes, TextChannel channel)
    {
        outputJokes.addAll(jokes);
        if (outputJokes.size() != 0)
        {
            Random rand = new Random();
            int i = rand.nextInt(outputJokes.size());
            EmbedManager.SendCustomEmbed(title, outputJokes.get(i), Color.decode("#7145c4"), channel, 30);
            outputJokes.clear();
        }

    }

}
