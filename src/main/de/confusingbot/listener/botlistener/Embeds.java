package main.de.confusingbot.listener.botlistener;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Embeds {

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public EmbedBuilder BotJoinEmbed() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#fcdf03"));
        builder.setTitle("\uD83D\uDCA1Usage");
        builder.setDescription("Use **'" + Main.prefix + "help'** to experience **all commands** of this bot!");
        builder.setFooter("Powerd by ConfusingGames");

        return builder;
    }

    public EmbedBuilder BotLeftEmbed(){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#fcdf03"));
        builder.setTitle("The Bot left the server");
        builder.setDescription("See you later^^");
        builder.setFooter("Powerd by ConfusingGames");

        return builder;
    }
}
