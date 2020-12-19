package de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
import de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UpdateQuestionChannels
{
    List<Integer> notificationTimes = new ArrayList<>();
    SQL sql = new SQL();

    //Needed Permissions
    Permission MANAGE_CHANNEL = Permission.MANAGE_CHANNEL;

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
            if(set != null)
            {
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
                    Member bot = guild.getSelfMember();
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
                    long timeleftInMinutes = CommandsUtil.getTimeBetweenTwoDates(currentTimeString, deleteTime, false);

                    //Update Channel Time after 5 hours
                    if (timeleftInMinutes <= (QuestionManager.startUpdatingDeleteTime * 60))
                        UpdateChannelDeleteTime(guild, channel);

                    //Send notifications
                    for (Integer notificationTime : notificationTimes)
                    {
                        ArrayList<Integer> validationArray = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
                        boolean trigger = validationArray.contains((int) (timeleftInMinutes % (notificationTime * 60))) && (Math.round(timeleftInMinutes / (notificationTime * 60)) == 1);

                        if (trigger)
                        {
                            //Message
                            QuestionManager.embeds.SendDeleteQuestionInfo(channel, member, Math.round(timeleftInMinutes / 60));
                        }
                    }

                    //If time Ended
                    if (timeleftInMinutes <= 0)
                    {
                        if (bot.hasPermission(channel, MANAGE_CHANNEL))
                        {
                            //Delete Channel
                            channel.delete().queue();

                            //SQL
                            sql.removeQuestionFromSQL(guildID, channelID, memberID);
                        }
                        else
                        {
                            //Error
                            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_CHANNEL, "QuestionCommand | I can't delete this question channel!");
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void UpdateChannelDeleteTime(Guild guild, TextChannel channel)
    {
        if (!channel.hasLatestMessage()) return;

        long lastMessageId = channel.getLatestMessageIdLong();
        Message latestMessage = CommandsUtil.getLatestesMessageByID(channel, lastMessageId);

        if (latestMessage != null && !latestMessage.getAuthor().isBot())
        {
            LocalDateTime messageCreationTime = latestMessage.getTimeCreated().toLocalDateTime();
            LocalDateTime newDeleteTime = CommandsUtil.AddXTime(messageCreationTime, QuestionManager.extraHoursAfterMessage, QuestionManager.hours);

            //SQL
            sql.UpdateDeleteTimeInSQL(guild.getIdLong(), channel.getIdLong(), newDeleteTime.format(QuestionManager.formatter));
        }
    }
}
