package main.de.confusingbot.commands.help;

import com.vdurmont.emoji.EmojiManager;
import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRoleManager;
import main.de.confusingbot.commands.cmds.admincmds.votecommand.VoteCommandManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.HierarchyException;

import java.awt.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandsUtil
{

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String[] messageToArgs(Message message)
    {
        String m = message.getContentDisplay();
        m = m.replace("   ", " ");
        m = m.replace("  ", " ");
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

    public static boolean reactEmote(String emoteString, TextChannel channel, long messageid, boolean add)
    {
        try
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
                if (EmojiManager.containsEmoji(emoteString) || VoteCommandManager.voteEmotes.contains(emoteString))
                {
                    if (add)
                        channel.addReactionById(messageid, emoteString).queue();
                    else
                        channel.removeReactionById(messageid, emoteString).queue();
                }
                else
                {
                    return false;
                }
            }
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    public static List<Long> getLatestMessageIds(MessageChannel channel)
    {
        List<Long> messageIds = new ArrayList<>();

        for (Message message : channel.getIterableHistory().cache(false))
        {
            messageIds.add(message.getIdLong());
        }
        return messageIds;
    }

    public static Message getLatestesMessageByID(TextChannel channel, long messageid)
    {
        for (Message m : channel.getIterableHistory().cache(false))
        {
            if (m.getIdLong() == messageid) return m;
        }

        return null;
    }

    public static void AddOrRemoveRoleFromAllMembers(Guild guild, long roleid, boolean add)
    {
        List<Member> members = guild.getMembers();

        Role role = guild.getRoleById(roleid);
        if (role == null) return;

        for (Member member : members)
        {
            if (!member.getUser().isBot())
            {
                try
                {
                    if (add)
                        guild.addRoleToMember(member, role).queue();
                    else
                        guild.removeRoleFromMember(member, role).queue();
                } catch (HierarchyException e)
                {
                    ReactRoleManager.embeds.BotHasNoPermissionToAssignRole(guild.getDefaultChannel(), role);
                    return;
                }
            }
        }
    }

    public static long getTimeLeft(String creationTimeString, int endAfter, boolean hours)
    {
        return endAfter - getTimeDifference(creationTimeString, hours);
    }

    public static long getTimeDifference(String creationTimeString, boolean hours)
    {
        long timeDifference = -1;

        //get CreationTime
        LocalDateTime creationTime = LocalDateTime.parse(creationTimeString, formatter);

        //Get currentTime
        String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(formatter);
        LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);

        //Calculate timeleft
        Duration duration = Duration.between(creationTime, currentTime);
        if (hours)
            timeDifference = duration.toHours();
        else
            timeDifference = duration.toMinutes();


        return timeDifference;
    }

    public static long getTimeBetweenTwoDates(String fromDateTimeString, String toDateTimeString, boolean hours)
    {
        LocalDateTime fromDateTime = LocalDateTime.parse(fromDateTimeString, formatter);
        LocalDateTime toDateTime = LocalDateTime.parse(toDateTimeString, formatter);

        long time;
        if (hours)
            time = fromDateTime.until(toDateTime, ChronoUnit.HOURS);
        else
            time = fromDateTime.until(toDateTime, ChronoUnit.MINUTES);

        return time;
    }

    public static LocalDateTime AddXTime(LocalDateTime date, long time, boolean hours)
    {
        if (hours)
            return date.plusHours(time);
        else
            return date.plusMinutes(time);
    }

    public static List<String> encodeString(String string, String splitChar)
    {
        List<String> wordsFixed = Arrays.asList(string.split(splitChar));
        List<String> words = new ArrayList<>();

        for (String wordFixed : wordsFixed) words.add(wordFixed);

        return words;
    }

    public static String codeString(List<String> strings, String splitChar)
    {
        String finalString = "";
        if (!strings.isEmpty())
        {
            for (String string : strings)
            {
                finalString += string + splitChar;
            }
            finalString = finalString.substring(0, finalString.length() - splitChar.length()).trim();
        }
        return finalString;
    }

    public static List<Integer> encodeInteger(String string, String splitChar)
    {
        if (splitChar == null || splitChar.equals("")) return null;

        List<String> words = Arrays.asList(string.split(splitChar));
        List<Integer> integers = new ArrayList<>();

        for (String word : words) if (isNumeric(word)) integers.add(Integer.parseInt(word));

        return integers;
    }

    public static String codeInteger(List<Integer> integers, String splitChar)
    {
        String finalString = "";
        if (!integers.isEmpty())
        {
            for (Integer integer : integers)
            {
                finalString += integer + splitChar;
            }
            finalString = finalString.substring(0, finalString.length() - ", ".length()).trim();
        }
        return finalString;
    }

    public static List<Long> encodeLong(String string, String splitChar)
    {
        if (splitChar == null || splitChar.equals("")) return null;

        List<String> words = Arrays.asList(string.split(splitChar));
        List<Long> longs = new ArrayList<>();

        for (String word : words) if (isNumeric(word)) longs.add(Long.parseLong(word));

        return longs;
    }

    public static String codeLong(List<Long> longs, String splitChar)
    {
        String finalString = "";
        if (!longs.isEmpty())
        {
            for (Long l : longs)
            {
                finalString += l + splitChar;
            }
            finalString = finalString.substring(0, finalString.length() - ", ".length()).trim();
        }
        return finalString;
    }

    public static String getEmote(Message message, String emoteString)
    {
        List<Emote> emotes = message.getEmotes();

        String finalEmoteString = "";
        if (!emotes.isEmpty())
        {
            for (Emote emote : emotes)
            {
                String emoteName = ":" + emote.getName() + ":";
                if (emoteName.equals(emoteString))
                    finalEmoteString += emote.getIdLong();
            }
        }
        else
        {
            String emote = emoteString;
            finalEmoteString += emote;
        }
        return finalEmoteString;
    }

    public static double[] toPrimitiveDoubleArray(List<Double> arrayList)
    {
        double result[] = new double[arrayList.size()];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = arrayList.get(i);
        }
        return result;
    }

    public static LocalDateTime DateTimeConverter(String dateTimeString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String[] parts = dateTimeString.split("T");
        String[] dateParts = parts[0].split("-");
        String year = dateParts[0];
        String month = dateParts[1];
        String day = dateParts[2];

        String[] timeParts = parts[1].substring(0, parts[1].indexOf(".")).split(":");
        String hour = timeParts[0];
        String minutes = timeParts[1];
        String seconds = timeParts[2];

        String newDateString = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;

        return LocalDateTime.parse(newDateString, formatter);
    }

    public static String buildWholeString(String[] args, int startIndex, int endIndex, List<Role> roles)
    {
        String finalString = "";
        for (int i = startIndex; i < endIndex; i++)
        {
            if (!roles.isEmpty())
            {
                boolean isRole = false;
                for (Role role : roles)
                {
                    if (args[i].contains(role.getName()))
                        isRole = true;
                }

                if (!isRole)
                    finalString += args[i] + " ";
            }
            else
            {
                finalString += args[i] + " ";
            }
        }
        finalString = finalString.substring(0, finalString.length() - " ".length()).trim();

        return finalString;
    }
}

