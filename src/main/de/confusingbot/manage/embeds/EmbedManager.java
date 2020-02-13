package main.de.confusingbot.manage.embeds;

import java.awt.Color;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.DeleteQuestionRunnable;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.QuestionManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class EmbedManager
{

    public static void SendErrorEmbed(String description, TextChannel channel, int timeInSeconds)
    {

        EmbedBuilder error = new EmbedBuilder();
        error.setColor(0xff3923);
        error.setTitle("🔴 Error");
        error.setDescription(description);

        if (timeInSeconds <= 0)
        {
            channel.sendMessage(error.build()).queue();
        }
        else
        {
            channel.sendMessage(error.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static void SendSuccessEmbed(String description, TextChannel channel, int timeInSeconds)
    {

        EmbedBuilder success = new EmbedBuilder();
        success.setColor(0x00FF40);
        success.setTitle("✔️ Success");
        success.setDescription(description);

        if (timeInSeconds <= 0)
        {
            channel.sendMessage(success.build()).queue();
        }
        else
        {
            channel.sendMessage(success.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static void SendInfoEmbed(String description, TextChannel channel, int timeInSeconds)
    {

        EmbedBuilder info = new EmbedBuilder();
        info.setColor(0xBF00FF);
        info.setTitle("💡 Information");
        info.setDescription(description);

        if (timeInSeconds <= 0)
        {
            channel.sendMessage(info.build()).queue();
        }
        else
        {
            channel.sendMessage(info.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static void SendUsageEmbed(String description, TextChannel channel, int timeInSeconds)
    {
        EmbedBuilder usage = new EmbedBuilder();
        usage.setColor(0xBF00FF);
        usage.setTitle("💡 Usage");
        usage.setDescription(description);

        if (timeInSeconds <= 0)
        {
            channel.sendMessage(usage.build()).queue();
        }
        else
        {
            channel.sendMessage(usage.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static void SendCustomEmbed(String title, String description, Color color, TextChannel channel, int timeInSeconds)
    {

        EmbedBuilder custom = new EmbedBuilder();
        custom.setColor(color);
        custom.setTitle(title);
        custom.setDescription(description);

        if (timeInSeconds <= 0)
        {
            channel.sendMessage(custom.build()).queue();
        }
        else
        {
            channel.sendMessage(custom.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static void SendCustomPrivateEmbed(String title, String description, Color color, User user)
    {

        EmbedBuilder custom = new EmbedBuilder();
        custom.setColor(color);
        custom.setTitle(title);
        custom.setDescription(description);

        user.openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(custom.build()).queue();
        });
    }

    public static long SendCustomEmbedGetMessageID(String title, String description, Color color, TextChannel channel)
    {

        long messageID = -1;

        EmbedBuilder custom = new EmbedBuilder();
        custom.setColor(color);
        custom.setTitle(title);
        custom.setDescription(description);

        messageID = channel.sendMessage(custom.build()).complete().getIdLong();

        return messageID;
    }

    public static void SendEmbed(EmbedBuilder builder, TextChannel channel, int timeInSeconds)
    {
        if (timeInSeconds <= 0)
        {
            channel.sendMessage(builder.build()).queue();
        }
        else
        {
            channel.sendMessage(builder.build()).queue(message -> {
                try
                {
                    message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                } catch (InterruptedException | ExecutionException e)
                {
                    System.err.println("Couldn't delete Message by ID " + message.getIdLong());
                }
            });
        }
    }

    public static long SendEmbedGetMessageID(EmbedBuilder builder, TextChannel channel)
    {

        long messageID = -1;

        messageID = channel.sendMessage(builder.build()).complete().getIdLong();

        return messageID;
    }

    public static void DeleteMessageByID(TextChannel channel, long messageID)
    {
        try
        {
            channel.deleteMessageById(messageID).submit().get();

        } catch (Exception e)
        {
            System.err.println("Couldn't delete Message by ID " + messageID + " on Guild " + channel.getGuild().getIdLong());
        }
    }
}

