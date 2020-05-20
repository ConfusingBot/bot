package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class UpdateRepeatInfo
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo");
            if(set != null)
            {
                while (set.next())
                {
                    long guildID = set.getLong("guildid");
                    long channelID = set.getLong("channelid");
                    int timeInHours = set.getInt("time");
                    int id = set.getInt("id");
                    String info = set.getString("info");
                    String title = set.getString("title");
                    String color = set.getString("color");


                    //Check if RepeatInfo is sendable
                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                    if (guild == null)
                    {
                        RepeatInfoCommandManager.sql.removeFormSQL(guildID, id);
                        return;
                    }
                    TextChannel channel = guild.getTextChannelById(channelID);
                    if (channel == null)
                    {
                        RepeatInfoCommandManager.sql.removeFormSQL(guildID, id);
                        return;
                    }

                    Member bot = guild.getSelfMember();

                    long differenceInMinutes = CommandsUtil.getTimeLeft(creationTime, timeInHours, false);
                    ArrayList<Integer> validationArray = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
                    boolean trigger = validationArray.contains((int) (differenceInMinutes % (timeInHours * 60)) * -1);

                    if (trigger)
                    {
                        if (bot.hasPermission(channel, MESSAGE_WRITE))
                            RepeatInfoCommandManager.embeds.SendInfoEmbed(channel, color, title, info);

                        //Reset creationTime because otherwise the differenceInMinutes number will increase endless
                        if (differenceInMinutes < -(RepeatInfoCommandManager.maxGapInHours * 60) + 10)
                            creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
