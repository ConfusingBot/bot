package main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommandManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.Guild;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BotInfo
{
    public String updateBotOnlineTime(LocalDateTime fromDateTime, boolean saveToSQL)
    {
        LocalDateTime toDateTime = LocalDateTime.now();

        String totalOnlineTime = InfoCommandManager.sql.getOnlineTime();

        //If sql does not exist!
        if (totalOnlineTime.isEmpty())
            totalOnlineTime = InfoCommandManager.defaultOnlineTime;

        //Get total times
        String[] totalTimesString = totalOnlineTime.split(", ");
        List<Long> totalTimes = new ArrayList<>();

        for (String totalTimeString : totalTimesString)
        {
            if (CommandsUtil.isNumeric(totalTimeString))
            {
                totalTimes.add(Long.parseLong(totalTimeString));
            }
            else
            {
                totalTimes.add((long) 0);
            }
        }

        try
        {
            String onlineTime = "";

            LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

            long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
            tempDateTime = tempDateTime.plusYears(years);

            long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
            tempDateTime = tempDateTime.plusMonths(months);

            long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(days);

            long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(hours);

            long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
            tempDateTime = tempDateTime.plusMinutes(minutes);

            long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

            //Get total Time
            long totalYears = years + totalTimes.get(0);

            long totalMonths = months + totalTimes.get(1);
            if (totalMonths >= 12)
            {
                totalMonths = totalMonths - 12;
                totalYears++;
            }

            long totalDays = days + totalTimes.get(2);
            if (totalDays >= 31)
            {
                totalDays = totalDays - 31;
                totalMonths++;
            }

            long totalHours = hours + totalTimes.get(3);
            if (totalHours >= 24)
            {
                totalHours = totalHours - 24;
                totalDays++;
            }

            long totalMinutes = minutes + totalTimes.get(4);
            if (totalMinutes >= 60)
            {
                totalMinutes = totalMinutes - 60;
                totalHours++;
            }

            long totalSeconds = seconds + totalTimes.get(5);
            if (totalSeconds >= 60)
            {
                totalSeconds = totalSeconds - 60;
                totalMinutes++;
            }

            if (totalYears > 0)
                onlineTime += totalYears + " y ";

            if (totalMonths > 0)
                onlineTime += totalMonths + " m ";

            if (totalDays > 0)
                onlineTime += totalDays + " d ";

            if (totalHours > 0)
                onlineTime += totalHours + " h ";

            if (totalMinutes > 0)
                onlineTime += totalMinutes + " m ";

            if (totalSeconds > 0)
                onlineTime += totalSeconds + " s";

            if (saveToSQL)
            {
                //SQL
                String sqlOnlineTimeString = totalYears + ", " + totalMonths + ", " + totalDays + ", " + totalHours + ", " + totalMinutes + ", " + totalSeconds;
                InfoCommandManager.sql.updateOnlineTime(sqlOnlineTimeString);
            }

            return onlineTime;
        } catch (Exception e)
        {
            e.printStackTrace();
            return InfoCommandManager.noInformationString;
        }
    }

    public String getTotalServers()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            total.set(jda.getGuilds().size());
        });

        if (total == null)
        {
            return InfoCommandManager.noInformationString;
        }
        else
        {
            return total.toString();
        }
    }

    public String getTotalChannels()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            int t = 0;
            List<Guild> guilds = jda.getGuilds();

            for (Guild guild : guilds)
            {
                t += guild.getChannels().size();
            }

            total.set(t);

        });

        if (total == null)
        {
            return InfoCommandManager.noInformationString;
        }
        else
        {
            return total.toString();
        }
    }

    public String getTotalMembers()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            int t = 0;
            List<Guild> guilds = jda.getGuilds();

            for (Guild guild : guilds)
            {
                t += guild.getMembers().size();
            }

            total.set(t);
        });

        if (total == null)
        {
            return InfoCommandManager.noInformationString;
        }
        else
        {
            return total.toString();
        }
    }
}
