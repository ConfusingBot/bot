package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.music.Music;
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
//TODO DELETE
        //START
        start.add("```yaml\n" + Main.prefix + "help useful\n``` ```Show you the useful commands```");
        start.add("```yaml\n" + Main.prefix + "help fun\n``` ```Show you the fun commands```");
        start.add("```yaml\n" + Main.prefix + "help music\n``` ```Show you the music commands```");
        start.add("```yaml\n" + Main.prefix + "help admin\n``` ```Show you the admin commands```");

        //USEFUL






        //MUSIC
        music.add("```yaml\n" + Main.prefix + "play [song/playlist Title/URL]\n``` ```Start playing a track and add aditional songs to the queue```");
        music.add("```yaml\n" + Main.prefix + "join\n``` ```ConfusingBot will join your channel```");
        music.add("```yaml\n" + Main.prefix + "leave\n``` ```ConfusingBot will leave your channel```");
        music.add("```yaml\n" + Main.prefix + "trackinfo\n``` ```Gives you some informations about the current playing track```");
        music.add("```yaml\n" + Main.prefix + "shuffle\n``` ```Shuffle your queue```");
        music.add("```yaml\n" + Main.prefix + "queue\n``` ```Offers you the MusicQueue```");
        music.add("```yaml\n" + Main.prefix + "queue clear\n``` ```Clear the MusicQueue```");
        music.add("```yaml\n" + Main.prefix + "queue delete [index]\n``` ```Delete the Track at index```");

        music.add("```yaml\n" + Main.prefix + "skip\n``` ```skip the current playing Song```");
        music.add("```yaml\n" + Main.prefix + "pause\n``` ```Pause/Resume the current Song```");


    }

    public static EmbedBuilder Start()
    {
        String description = "";
        description += "**\uD83C\uDF10️HELP**";
        for (String s : start)
        {
            description += (s + "\n");
        }

        return buildEmbed(description);
    }

    public static EmbedBuilder Fun()
    {
        String description = "";
        description += "\n**\uD83E\uDD23FUN**";
        for (String f : fun)
        {
            description += (f + "\n");
        }

        return buildEmbed(description);
    }

    public static EmbedBuilder Useful()
    {
        String description = "";
        description += "**✅USEFUL**";
        for (String u : useful)
        {
            description += (u + "\n");
        }

        return buildEmbed(description);
    }

    public static EmbedBuilder Music()
    {
        String description = "";
        description += "\n**\uD83C\uDFB6MUSIC**";
        for (String m : music)
        {
            description += (m + "\n");
        }

        return buildEmbed(description);
    }

    public static EmbedBuilder Admin()
    {
        String description = "";
        description += "\n**⚠ADMIN**";
        for (String a : admin)
        {
            description += (a + "\n");
        }

        return buildEmbed(description);
    }

    private static EmbedBuilder buildEmbed(String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0x23cba7);

        builder.setDescription(description);
        builder.setFooter("Powerd by ConfusingGames");
        builder.setTimestamp(OffsetDateTime.now());

        return builder;
    }

    public static EmbedBuilder getHelp(String[] args){
        EmbedBuilder helpBuilder;
        if (args.length == 1) {
             helpBuilder = Start();
        }else{
            switch (args[1]){
                case "fun":
                    helpBuilder = Fun();
                    break;
                case "useful":
                    helpBuilder = Useful();
                    break;
                case "music":
                    helpBuilder = Music();
                    break;
                case "admin":
                    helpBuilder = Admin();
                    break;
                default:
                    helpBuilder = Start();
                    break;
            }
        }
        return  helpBuilder;

    }

}
