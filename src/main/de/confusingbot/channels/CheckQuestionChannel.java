package main.de.confusingbot.channels;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckQuestionChannel
{

    int maxTimeInHours = 24;
    List<Integer> notificationTimes = new ArrayList<>();


    public CheckQuestionChannel()
    {
        //Add notificationTimes
        notificationTimes.add(1);
        notificationTimes.add(12);
        notificationTimes.add(24);
    }

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM questioncommand");
            while (set.next())
            {
                String creationTime = set.getString("creationtime");
                long channelID = set.getLong("channelid");
                long memberID = set.getLong("memberid");
                long guildID = set.getLong("guildid");

                //TODO create a state when member guild or channel is null
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null) return;
                Member member = guild.getMemberById(memberID);
                if (member == null) return;
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null) return;

                //HANDLE TIME

                long timeleft = getTimeLeft(creationTime, channel);
                System.out.println("TimeLeft of question: " + timeleft);

                for (Integer notificationTime : notificationTimes)
                {
                    if (timeleft == notificationTime)
                    {
                        //System.out.println("Notification " + timeleft);

                        EmbedManager.SendCustomEmbedGetMessageID("This question will be closed in " + timeleft + " Minutes",
                                member.getAsMention() + " If nobody writes anything within " + timeleft + " hours, this question will be deleted\uD83D\uDE10",
                                Color.decode("#e03d14"), channel);
                    }
                }

                if (timeleft <= 0)
                {
                    //Delete Channel
                    channel.delete().queue();
                    //SQL
                    removeFromSQL(guildID, channelID, memberID);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Calculate TimeLeft
    private long getTimeLeft(String creationTimeString, TextChannel channel)
    {
        long timeLeft = -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get CreationTime
        LocalDateTime creationTime = LocalDateTime.parse(creationTimeString, formatter);

        //Get currentTime
        String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(formatter);
        LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);

        //Calculate timeleft
        Duration duration = Duration.between(creationTime, currentTime);
        long differentInHours = (duration.toMinutes() - 60);//if in toMinutes - 60 and not - 1!
        System.out.println("Different in minutes " + differentInHours);
        timeLeft = maxTimeInHours - differentInHours;

        //TODO optimize Ausnahme wenn nachricht versendet wurde wieder um 2h verlängern oder so..
        /*
        duration = Duration.between(creationTime, getLastChannelMessageSentTime(channel));
        long timeBetweenlastMessage = (duration.toHours() - 60);//if in toMinutes - 60 and not - 1!
        if (timeBetweenlastMessage <= 3 && timeLeft <= 10)
        {
            timeLeft += timeBetweenlastMessage;
        }
        */

        return timeLeft;
    }

    private LocalDateTime getLastChannelMessageSentTime(TextChannel channel)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime lastMessageTime;

        Message lastMessage = channel.getHistory().getMessageById(channel.getLatestMessageIdLong());
        String lastMessageTimeString = lastMessage.getTimeCreated().toLocalDateTime().format(formatter);
        lastMessageTime = LocalDateTime.parse(lastMessageTimeString, formatter);

        return lastMessageTime;
    }

    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    private void removeFromSQL(long guildid, long channelid, long memberid)
    {
        LiteSQL.onUpdate("DELETE FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND memberid = " + memberid);
    }

}
