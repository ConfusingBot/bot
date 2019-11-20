package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories;

import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeCategory;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class JackNorisJokes implements JokeCategory
{
    private List<String> jackNorisJokes = new ArrayList<>();
    private String title = "Jack Norris Facts\\uD83D\\uDE09";

    public JackNorisJokes()
    {
        addJackNorisJokes();
    }

    @Override
    public void performJoke(TextChannel channel, JokeManager manager)
    {
        manager.GenerateJokeEmbed(title, jackNorisJokes, channel);
    }

    private void addJackNorisJokes()
    {
        addJackNorisJoke("Chuck Norris threw a grenade and killed 50 people, then it exploded.");
        addJackNorisJoke("Chuck Norris can kill two stones with one bird.");
        addJackNorisJoke("Chuck Norris knows Victoria's secret.");
        addJackNorisJoke("Chuck Norris counted to infinity. Twice.");
        addJackNorisJoke("Chuck Norris can strangle you with a cordless phone.");
        addJackNorisJoke("Chuck Norris was once charged with three attempted murders in Boulder County, but the Judge quickly dropped the charges because Chuck Norris does not \"attempt\" murder.");
        addJackNorisJoke("When Chuck Norris gives you the finger, he's telling you how many seconds you have left to live.");
        addJackNorisJoke("Once a cobra bit Chuck Norris' leg. After five days of excruciating pain, the cobra died.");
        addJackNorisJoke("When the Boogeyman goes to sleep every night he checks his closet for Chuck Norris.");
        addJackNorisJoke("Chuck actually died four years ago, but the Grim Reaper can't get up the courage to tell him.");
    }

    private void addJackNorisJoke(String joke)
    {
        jackNorisJokes.add(joke);
    }


}
