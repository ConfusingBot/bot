package main.de.confusingbot.commands.help;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandsUtil
{

    public static String[] messageToArgs(Message message)
    {
        String m = message.getContentDisplay();
        m.replace("   ", " ");
        m.replace("  ", " ");
        String[] args = m.substring(Main.prefix.length()).split(" ");

        return args;
    }

    public static boolean isNumeric(String strNum)
    {
        try
        {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException | NullPointerException nfe)
        {
            return false;
        }
        return true;
    }

    public static void sleepXSeconds(float seconds)
    {
        int milliseconds = (int) (seconds * 1000);
        try
        {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isColor(String hexColor)
    {
        try
        {
            Color color = Color.decode(hexColor);
            return true;
        } catch (NumberFormatException e)
        {

        }
        return false;
    }

    public static List<String> getEmotes(Message message, List<String> emoteString)
    {
        List<Emote> emotesInMessage = message.getEmotes();
        List<String> customemotes = new ArrayList<>();

        List<String> emotes = new ArrayList<>();

        for (Emote emote : emotesInMessage)
        {
            emotes.add(String.valueOf(emote.getIdLong()));
            customemotes.add(":" + emote.getName() + ":");
        }

        for (int i = 0; i < emoteString.size(); i++)
        {
            String emote = emoteString.get(i);
            if (!customemotes.contains(emote))
            {
                emotes.add(emote);
            }
        }
        return emotes;
    }

    public static void reactEmote(String emoteString, TextChannel channel, long messageid, boolean add)
    {
        if (CommandsUtil.isNumeric(emoteString))//if emoteString is a emoteID
        {
            Emote emote = channel.getGuild().getEmoteById(Long.parseLong(emoteString));
            if (add)
                channel.addReactionById(messageid, emote).queue();
            else
                channel.removeReactionById(messageid, emote).queue();
        }
        else
        {
            if (add)
                channel.addReactionById(messageid, emoteString).queue();
            else
                channel.removeReactionById(messageid, emoteString).queue();
        }
    }

    public static List<Long> getLatestMessages(MessageChannel channel)
    {
        CommandsUtil.sleepXSeconds(0.5f);
        List<Long> messages = new ArrayList<>();

        for (Message message : channel.getIterableHistory().cache(false))
        {
            messages.add(message.getIdLong());
        }
        return messages;
    }
}

