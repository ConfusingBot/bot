package main.de.confusingbot.manage.embeds;

import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.DeleteQuestionRunnable;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.QuestionManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(error.build()).queue();
            }
            else
            {
                error.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(error.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(success.build()).queue();
            }
            else
            {
                success.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(success.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(info.build()).queue();
            }
            else
            {
                info.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(info.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(usage.build()).queue();
            }
            else
            {
                usage.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(usage.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(custom.build()).queue();
            }
            else
            {
                custom.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(custom.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
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

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            messageID = channel.sendMessage(custom.build()).complete().getIdLong();

        return messageID;
    }

    public static void SendEmbed(EmbedBuilder builder, TextChannel channel, int timeInSeconds)
    {
        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(builder.build()).queue();
            }
            else
            {
                builder.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(builder.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
                    }
                });
            }
    }

    public static void SendMessage(String text, TextChannel channel, int timeInSeconds)
    {
        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(text).queue();
            }
            else
            {
                channel.sendMessage(text).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
                    }
                });
            }
    }

    public static long SendEmbedGetMessageID(EmbedBuilder builder, TextChannel channel)
    {
        long messageID = -1;

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            messageID = channel.sendMessage(builder.build()).complete().getIdLong();

        return messageID;
    }

    public static void DeleteMessageByID(TextChannel channel, long messageID)
    {
        try
        {
            if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL) && channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_MANAGE))
                channel.deleteMessageById(messageID).submit().get();
        } catch (Exception e)
        {
            System.err.println("Couldn't delete Message by ID " + messageID + " on Guild " + channel.getGuild().getIdLong());
            System.err.println(e);
        }
    }

    public static void SendNoPermissionEmbed(TextChannel channel, Permission permission, String description)
    {
        int timeInSeconds = 10;
        EmbedBuilder noPermission = new EmbedBuilder();
        noPermission.setColor(0xBF00FF);
        noPermission.setTitle("⚠️ No Permission");
        noPermission.setDescription("`Sry I can't execute this command ⚡️`\n ```yaml\n" + permission.name() + "```\n" + description);
        noPermission.setTimestamp(OffsetDateTime.now());

        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_WRITE))
            if (timeInSeconds <= 0)
            {
                channel.sendMessage(noPermission.build()).queue();
            }
            else
            {
                noPermission.setFooter("This message will be deleted in " + timeInSeconds + "s (" + Main.prefix + "autoremove)");
                channel.sendMessage(noPermission.build()).queue(message -> {
                    try
                    {
                        if (channel.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_CHANNEL))
                            message.delete().submitAfter(timeInSeconds, TimeUnit.SECONDS).get();
                    } catch (InterruptedException | ExecutionException e)
                    {
                        System.err.println("Couldn't delete Message by ID " + message.getIdLong() + " on Guild " + channel.getGuild().getIdLong());
                        System.err.println(e);
                    }
                });
            }
    }
}

