package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateVotes
{
    //Called all 5Min
    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM votecommand");
            while (set.next())
            {
                long id = set.getLong("id");
                long guildid = set.getLong("guildid");
                long channelid = set.getLong("channelid");
                long messageid = set.getLong("messageid");
                int endTime = set.getInt("endTime");
                String creationTime = set.getString("creationtime");

                long timeleft = getTimeLeft(creationTime, endTime);

                if (timeleft <= 0)
                {

                    //SQL
                    VoteCommandManager.sql.removeFromSQL(guildid, id);

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildid);
                    if (guild != null)
                    {
                        TextChannel channel = guild.getTextChannelById(channelid);
                        if (channel != null)
                        {
                            if (CommandsUtil.getLatestMessages(channel).contains(messageid))
                            {
                                CommandsUtil.reactEmote("❌", channel, messageid, true);

                                //Message
                                VoteCommandManager.embeds.SendResultEmbed(channel);
                            }
                            else
                            {
                                channel.sendMessage("Couldn't Send result!").queue();
                            }

                        }
                    }
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
        long differentInHours = (duration.toMinutes());//if in toMinutes - 60 and not - 1!
        //System.out.println("Different in minutes " + differentInHours);
        timeLeft = endTime - differentInHours;

        return timeLeft;
    }
}
