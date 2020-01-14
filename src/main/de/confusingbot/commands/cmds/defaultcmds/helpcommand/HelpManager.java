package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class HelpManager
{
    public static List<String> start = new ArrayList<>();
    public static List<String> fun = new ArrayList<>();
    public static List<String> useful = new ArrayList<>();
    public static List<String> music = new ArrayList<>();
    public static List<String> admin = new ArrayList<>();


    public static void insertHelp()
    {
        //START
        start.add("```yaml\n" + Main.prefix + "help useful\n``` ```Show you the useful commands```");
        start.add("```yaml\n" + Main.prefix + "help fun\n``` ```Show you the fun commands```");
        start.add("```yaml\n" + Main.prefix + "help music\n``` ```Show you the music commands```");
        start.add("```yaml\n" + Main.prefix + "help admin\n``` ```Show you the admin commands```");
    }

    public static ArrayList<EmbedBuilder> getHelpBuilder(String[] args)
    {
        ArrayList<EmbedBuilder> helpBuilder = new ArrayList<>();
        if (args.length == 1)
        {
            helpBuilder.add(Start());
        }
        else
        {
            switch (args[1])
            {
                case "fun":
                    helpBuilder.add(Fun());
                    break;
                case "useful":
                    helpBuilder.add(Useful());
                    break;
                case "music":
                    helpBuilder.add(Music());
                    break;
                case "admin":
                    helpBuilder.addAll(Admin());
                    break;
                default:
                    helpBuilder.add(Start());
                    break;
            }
        }
        return helpBuilder;
    }

    private static EmbedBuilder buildEmbed(String description, boolean isEnd)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0x23cba7);

        if (isEnd)
        {
            builder.setDescription(description + "\nYou need more help?\nhttps://discord.gg/xc82F8M");
            builder.setFooter("Powered by ConfusingGames");
            builder.setTimestamp(OffsetDateTime.now());
        }
        else
        {
            builder.setDescription(description);
        }
        return builder;
    }

    //=====================================================================================================================================
    //Embeds
    //=====================================================================================================================================
    public static EmbedBuilder Start()
    {
        String description = "";
        description += "**\uD83C\uDF10️HELP**";
        for (String s : start)
        {
            description += (s + "\n");
        }

        return buildEmbed(description, true);
    }

    public static EmbedBuilder Fun()
    {
        String description = "";
        description += "\n**\uD83E\uDD23FUN**";
        for (String f : fun)
        {
            description += (f + "\n");
        }

        return buildEmbed(description, true);
    }

    public static EmbedBuilder Useful()
    {
        String description = "";
        description += "**✅USEFUL**";
        for (String u : useful)
        {
            description += (u + "\n");
        }

        return buildEmbed(description, true);
    }

    public static EmbedBuilder Music()
    {
        String description = "";
        description += "\n**\uD83C\uDFB6MUSIC**";
        for (String m : music)
        {
            description += (m + "\n");
        }

        return buildEmbed(description, true);
    }

    public static ArrayList<EmbedBuilder> Admin()
    {
        ArrayList<EmbedBuilder> helpBuilder = new ArrayList<>();
        String description = "";
        description += "\n**⚠ADMIN**";
        for (int i = 0; i < admin.size(); i++)
        {
            if (i % 5 == 0 && i != 0)
            {
                helpBuilder.add(buildEmbed(description, false));
                description = "";
            }
            description += (admin.get(i) + "\n");
        }
        helpBuilder.add(buildEmbed(description, true));

        return helpBuilder;
    }
}
