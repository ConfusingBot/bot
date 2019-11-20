package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.categories;

import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeCategory;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.List;

public class GeneralJokes implements JokeCategory
{
    private static List<String> generalJokes = new ArrayList<>();
    private String title = "Joke\\uD83E\\uDD23";

    public void GeneralJokes()
    {
        addGeneralJokes();
    }

    @Override
    public void performJoke(TextChannel channel, JokeManager manager)
    {
        manager.GenerateJokeEmbed(title, generalJokes, channel);
    }

    private static void addGeneralJokes()
    {
        addGeneralJoke("Doctor: \"I'm sorry but you suffer from a terminal illness and have only 10 to live.\"\n" +
                "\n" +
                "Patient: \"What do you mean, 10? 10 what? Months? Weeks?!\"\n" +
                "\n" +
                "Doctor: \"Nine.\" ");
        addGeneralJoke("My old aunts would come and tease me at weddings, “Well Sarah? Do you think you’ll be next?”\n" +
                "-\n" +
                "We’ve settled this quickly once I’ve started doing the same to them at funerals.");
        addGeneralJoke("A doctor accidentally prescribes his patient a laxative instead of a coughing syrup.\n" +
                "-\n" +
                "Three days later the patient comes for a check-up and the doctor asks: “Well? Are you still coughing?”\n" +
                "-\n" +
                "The patient replies: “No. I’m afraid to.”  ");
        addGeneralJoke("Job interviewer: “And where would you see yourself in five years’ time Mr. Jeffries?\"\n" +
                "-\n" +
                "Mr. Jeffries: \"Personally I believe my biggest weakness is in listening.\"");
        addGeneralJoke("An old grandma brings a bus driver a bag of peanuts every day.\n" +
                "\n" +
                "First the bus driver enjoyed the peanuts but after a week of eating them he asked: \"Please granny, don't bring me peanuts anymore. Have them yourself.\".\n" +
                "\n" +
                "The granny answers: \"You know, I don't have teeth anymore. I just prefer to suck the chocolate around them.\"");
        addGeneralJoke("Dentist: “This will hurt a little.”\n" +
                "\n" +
                "Patient: “OK.”\n" +
                "\n" +
                "Dentist: “I’ve been having an affair with your wife for a while now.”");
        addGeneralJoke("I got another letter from this lawyer today. It said “Final Notice”. Good that he will not bother me anymore. ");
        addGeneralJoke("An eskimo brings his friend to his home for a visit. When they arrive, his friend asks, puzzled – “So where’s your igloo?”\n" +
                "-\n" +
                "The friend replies “Oh no, I must’ve left the iron on…” ");
        addGeneralJoke("\"Can you please hold my hand?\"A mother asks her son: \"Anton, do you think I’m a bad mom?\"\n" +
                "-\n" +
                "Son: \"My name is Paul.\"");
        addGeneralJoke("Doctor: You're obese.\n" +
                "-\n" +
                "Patient: For that I definitely want a second opinion.\n" +
                "-\n" +
                "Doctor: You’re quite ugly, too.");
        addGeneralJoke("Two donkeys are standing at a roadside, one asks the other: So, shall we cross?\n" +
                "-\n" +
                "The other shakes his head: \"No way, look at what happened to the zebra.\"");
        addGeneralJoke("Guest to the waiter: “Can you bring me what the lady at the next table is having?”\n" +
                "-\n" +
                "Waiter: “Sorry, sir, but I’m pretty sure she wants to eat it herself.”");
        addGeneralJoke("\"Mom, where do tampons go?\"\n" +
                "\n" +
                "\"Where the babies come from, darling.\"\n" +
                "\n" +
                "\"In the stork?\"");
        addGeneralJoke("A husband and a wife sit at the table, having dinner. The woman drops a bit of tomato sauce on her white top. \"Oh no, I look like a pig!\"\n" +
                "\n" +
                "The man nods, \"And you dropped tomato sauce on your top!\"");
        addGeneralJoke("One company owner asks another: “Tell me, Bill, how come your employees are always on time in the mornings?”\n" +
                "\n" +
                "Bill replies: “Easy. 30 employees and 20 parking spaces.”");
    }

    public static void addGeneralJoke(String joke)
    {
        generalJokes.add(joke);
    }


}
