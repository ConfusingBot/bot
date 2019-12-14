package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

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

public class UpdateQuestionChannels
{

    int maxTimeInHours = 24;
    List<Integer> notificationTimes = new ArrayList<>();
    SQL sql = new SQL();


    public UpdateQuestionChannels()
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
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null) return;

                //HANDLE TIME
                long timeleft = getTimeLeft(creationTime, maxTimeInHours);
                System.out.println("TimeLeft of question: " + timeleft);

                List<Integer> overdueNotificationTimes = new ArrayList<>();
                for (Integer notificationTime : notificationTimes)
                {
                    if (timeleft <= notificationTime)
                    {
                        overdueNotificationTimes.add(notificationTime);
                    }
                }

                //Message
                QuestionManager.embeds.SendDeleteQuestionInfo(channel, member, overdueNotificationTimes.get(overdueNotificationTimes.size() - 1));

                if (timeleft <= 0)
                {
                    //Delete Channel
                    channel.delete().queue();
                    //SQL
                    sql.removeQuestionFromSQL(guildID, channelID, memberID);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Calculate TimeLeft
    private long getTimeLeft(String creationTimeString, int endTime)
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
        long differentInHours = (duration.toHours());
        //System.out.println("Different in minutes " + differentInHours);
        timeLeft = endTime - differentInHours;

        return timeLeft;
    }
}
