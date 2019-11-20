package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories;

import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeCategory;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class MotherJokes implements JokeCategory
{
    private List<String> motherJokes = new ArrayList<>();
    private String title = "Mother Joke\\uD83D\\uDE09";

    public MotherJokes(){
        addMotherJokes();
    }

    @Override
    public void performJoke(TextChannel channel, JokeManager manager)
    {
          manager.GenerateJokeEmbed(title, motherJokes, channel);
    }

    private void addMotherJokes()
    {
        addMotherJoke("Yo momma is so fat when she got on the scale it said, \"I need your weight not your phone number.\" ");
        addMotherJoke("Yo momma is so fat, I took a picture of her last Christmas and it's still printing.");
        addMotherJoke("Yo momma is so fat that when she went to the beach a whale swam up and sang, \"We are family, even though you're fatter than me.\" ");
        addMotherJoke("Yo mamma is so ugly when she tried to join an ugly contest they said, \"Sorry, no professionals.\" ");
        addMotherJoke("Yo momma's so fat and old when God said, \"Let there be light,\" he asked your mother to move out of the way. ");
        addMotherJoke("Yo momma's so fat, that when she fell, no one was laughing but the ground was cracking up.");
        addMotherJoke("Yo momma is so fat when she sat on WalMart, she lowered the prices. ");
        addMotherJoke("Yo momma is so stupid when an intruder broke into her house, she ran downstairs, dialed 9-1-1 on the microwave, and couldn't find the \"CALL\" button. ");
        addMotherJoke("Yo momma is so fat that Dora can't even explore her!");
        addMotherJoke("Your momma is so ugly she made One Direction go another direction.");
        addMotherJoke("Yo momma is so fat her bellybutton gets home 15 minutes before she does.");
        addMotherJoke("Yo momma's so stupid, she put two quarters in her ears and thought she was listening to 50 Cent. ");
    }

    public void addMotherJoke(String joke)
    {
        motherJokes.add(joke);
    }
}
