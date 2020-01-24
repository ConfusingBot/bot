package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateQuestionChannels
{
    List<Integer> notificationTimes = new ArrayList<>();
    SQL sql = new SQL();

    public UpdateQuestionChannels()
    {
        //Add notificationTimes
        notificationTimes.add(1);
        notificationTimes.add(12);
        notificationTimes.add(23);
    }

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM questioncommand");
            while (set.next())
            {
                String creationTime = set.getString("creationtime");
                String deleteTime = set.getString("deletetime");
                long channelID = set.getLong("channelid");
                long memberID = set.getLong("memberid");
                long guildID = set.getLong("guildid");

                //Check if Question does exist
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null)
                {
                    //SQL
                    sql.removeQuestionFromSQL(guildID, channelID, memberID);
                    return;
                }

                Member member = guild.getMemberById(memberID);
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null)
                {
                    //SQL
                    sql.removeQuestionFromSQL(guildID, channelID, memberID);
                    return;
                }

                //Get CurrentTime
                String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(CommandsUtil.formatter);

                //HANDLE TIME
                long timeleft = CommandsUtil.getTimeBetweenTwoDates(currentTimeString, deleteTime, QuestionManager.hours);
                System.out.println("TimeLeft of question in Minutes: " + timeleft);

                //Update Channel Time
                if (timeleft <= 5)
                    UpdateChannelTime(guild, channel);

                //Send notifications
                for (Integer notificationTime : notificationTimes)
                {
                    if (timeleft == notificationTime)
                    {
                        //Message
                        QuestionManager.embeds.SendDeleteQuestionInfo(channel, member, timeleft);
                    }
                }

                //If time Ended
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

    private void UpdateChannelTime(Guild guild, TextChannel channel)
    {
        Message latestMessage = CommandsUtil.getLatestesMessageByID(channel, channel.getLatestMessageIdLong());

        if (!latestMessage.getAuthor().isBot())
        {
            LocalDateTime messageCreationTime = latestMessage.getTimeCreated().toLocalDateTime();
            LocalDateTime newDeleteTime = CommandsUtil.AddXTime(messageCreationTime, QuestionManager.addHoursAfterActivity, QuestionManager.hours);

            //SQL
            sql.UpdateDeleteTimeInSQL(guild.getIdLong(), channel.getIdLong(), newDeleteTime.format(QuestionManager.formatter));
        }
    }
}
