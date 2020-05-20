package main.de.confusingbot.listener.botlistener;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.function.Consumer;


public class BotListener extends ListenerAdapter
{

    Embeds embeds = BotListenerManager.embeds;
    SQL sql = BotListenerManager.sql;

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        System.out.println("Bot joined server " + event.getGuild().getName());

        //SQL
        sql.AddGuildToSQL(event.getGuild().getIdLong(), event.getGuild().getName());

        //Message
        EmbedBuilder builder = embeds.BotJoinEmbed();

        //Send private Message
        Consumer<? super Throwable> callback = (response) -> System.out.println("Failed to send private Message");
        event.getGuild().getOwner().getUser().openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(builder.build()).queue(null, callback);
        });

        //Send in the default Channel
        TextChannel channel = event.getGuild().getDefaultChannel();
        if (channel != null)
            EmbedManager.SendEmbed(builder, channel, 0);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        System.out.println("Bot left server " + event.getGuild().getName());

        //SQL
        if (event.getGuild() != null)
            sql.RemoveGuildFromSQL(event.getGuild().getIdLong());
    }
}
