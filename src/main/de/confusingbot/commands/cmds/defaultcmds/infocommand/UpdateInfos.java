package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        List<String> datesString = new ArrayList<>();
        List<String> membersString = new ArrayList<>();
        List<Long> guildIDs = new ArrayList<>();
        //==================================================================================================
        //SQL
        //==================================================================================================
        ResultSet set = LiteSQL.onQuery("SELECT * FROM servers");
        try
        {
            while (set.next())
            {
                guildIDs.add(set.getLong("guildid"));
                datesString.add(set.getString("dates"));
                membersString.add(set.getString("members"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        //==================================================================================================
        //END SQL
        //==================================================================================================

        if (guildIDs.isEmpty()) return;

        for (int i = 0; i < guildIDs.size(); i++)
        {
            Guild guild = Main.INSTANCE.shardManager.getGuildById(guildIDs.get(i));

            List<String> dates = new ArrayList<>();
            if (datesString.get(i) != null && !datesString.equals(""))
                dates = CommandsUtil.encodeString(datesString.get(i), ", ");

            List<Integer> members = new ArrayList<>();
            if (membersString.get(i) != null && !members.equals(""))
                members = CommandsUtil.encodeInteger(membersString.get(i), ", ");

            Date currentDate = new Date();
            String currentDateString = formatter.format(currentDate);

            if (dates.isEmpty() || !dates.get(dates.size() - 1).equals(currentDateString))
            {
                dates.add(currentDateString);
                members.add(guild.getMemberCount());
                String newDateString = CommandsUtil.codeString(dates, ", ");
                String newMembersString = CommandsUtil.codeInteger(members, ", ");

                //Update SQL
                InfoCommandManager.sql.UpdateMembersInServers(guildIDs.get(i), newMembersString);
                InfoCommandManager.sql.UpdateDatesInServers(guildIDs.get(i), newDateString);
            }
        }


    }
}
