package main.de.confusingbot.listener;

import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        System.out.println("Bot joined server " + event.getGuild().getName());

        LiteSQL.onUpdate("INSERT INTO servers(guildid, name) VALUES(" +
                event.getGuild().getIdLong() + ", '" + event.getGuild().getName() + "')");


        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#fcdf03"));
        builder.setTitle("\uD83D\uDCA1Usage");
        builder.setDescription("Use **'- help'** to experience **all commands** of this bot!");
        builder.setFooter("Powerd by ConfusingGames");
        event.getGuild().getOwner().getUser().openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(builder.build()).queue();
        });

        try {
            event.getGuild().getDefaultChannel().sendMessage(builder.build()).queue();
        }catch (InsufficientPermissionException e){
            //no permission in this channel
        }

    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        System.out.println("Bot left server " + event.getGuild().getName());

        for (String name : SQLManager.tabelNames){
            LiteSQL.onUpdate("DELETE FROM " + name + " WHERE "
                    + "guildid = " + event.getGuild().getIdLong());
        }
    }
}
