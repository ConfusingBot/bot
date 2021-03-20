package de.confusingbot.commands.cmds.defaultcmds.infocommand;

import de.confusingbot.Main;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateInfos
{
    public void onSecond()
    {
        updateServerMemberCount();
    }

    private void updateServerMemberCount()
    {
        System.out.println("Update MemberGraph from all servers");
        ResultSet set = SQLManager.onQuery("SELECT * FROM servers");
        if(set != null)
        {
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

                        //Get Current Date
                        LocalDateTime currentDate = OffsetDateTime.now().toLocalDateTime();
                        String currentDateString = InfoCommandManager.formatter.format(currentDate);

                        if (dates.isEmpty() || dates.size() <= 0 || !dates.get(dates.size() - 1).equals(currentDateString))
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
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("End Update MemberGraph from all servers");
    }
}

