package main.de.confusingbot.commands.help;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

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

    public static List<Long> getLatestMessageIds(MessageChannel channel)
    {
        List<Long> messageIds = new ArrayList<>();

        for (Message message : channel.getIterableHistory().cache(false))
        {
            messageIds.add(message.getIdLong());
        }
        return messageIds;
    }

    public static Message getLatestesMessageByID(TextChannel channel, long messageid) {
        Message message = null;
        MessageHistory history = channel.getHistory();

        message = history.getMessageById(messageid);

        return message;
    }

    public static void AddOrRemoveRoleFromAllMembers(Guild guild, long roleid, boolean add){
        List<Member> members = guild.getMembers();

        Role role = guild.getRoleById(roleid);
        if(role == null) return;

        for(Member member : members){
            if(!member.getUser().isBot()){
                if(add)
                    guild.addRoleToMember(member, role).queue();
                else
                    guild.removeRoleFromMember(member, role).queue();
            }
        }
    }


}

