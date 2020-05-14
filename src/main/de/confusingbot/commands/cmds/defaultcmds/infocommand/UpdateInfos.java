package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.listener.botlistener.BotListenerManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateInfos
{
    public void onSecond()
    {
        updateServerMemberCount();
    }

    private void updateServerMemberCount()
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM servers");
        try
        {
            while (set.next())
            {
                String dateString = set.getString("dates");
                String memberString = set.getString("members");
                long guildId = set.getLong("guildid");

                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildId);

                if (guild != null)
                {
                    List<String> dates = new ArrayList<>();
                    if (dateString != null && !dateString.equals(""))
                        dates = CommandsUtil.encodeString(dateString, ", ");

                    List<Integer> members = new ArrayList<>();
                    if (memberString != null && !memberString.equals(""))
                        members = CommandsUtil.encodeInteger(memberString, ", ");

                    LocalDateTime currentDate = OffsetDateTime.now().toLocalDateTime();
                    String currentDateString = InfoCommandManager.formatter.format(currentDate);

                    if (dates.isEmpty() || !dates.get(dates.size() - 1).equals(currentDateString))
                    {
                        dates.add(currentDateString);
                        members.add(guild.getMemberCount());
                        String newDateString = CommandsUtil.codeString(dates, ", ");
                        String newMembersString = CommandsUtil.codeInteger(members, ", ");

                        //Update SQL
                        InfoCommandManager.sql.UpdateMembersInServers(guildId, newMembersString);
                        InfoCommandManager.sql.UpdateDatesInServers(guildId, newDateString);
                    }
                }
                else
                {
                    //Remove Guild form SQL
                    BotListenerManager.sql.RemoveGuildFromSQL(guildId);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}

