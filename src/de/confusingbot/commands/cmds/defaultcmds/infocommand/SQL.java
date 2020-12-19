package de.confusingbot.commands.cmds.defaultcmds.infocommand;

import de.confusingbot.manage.sql.LiteSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //ClientInfo
    //=====================================================================================================================================
    public List<Long> getRoleBorders(long guildID)
    {
        List<Long> roleBorderIDs = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guildID);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    roleBorderIDs.add(set.getLong("roleid"));
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleBorderIDs;
    }

    //===========================================================================================================================
    //BotInfo
    //===========================================================================================================================
    public void updateOnlineTime(String onlineTime)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM bot");
        try
        {
            if (set != null && set.next())
            {
                LiteSQL.onUpdate("UPDATE bot SET onlinetime = '" + onlineTime + "'");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void addBotToSQL()
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM bot");
        try
        {
            if (set != null && !set.next())
            {
                LiteSQL.onUpdate("INSERT INTO bot(onlinetime, users) VALUES ('" +
                        InfoCommandManager.defaultOnlineTime + "', " + 0 + ")");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String getOnlineTime()
    {
        String onlineTime = "";
        ResultSet set = LiteSQL.onQuery("SELECT * FROM bot");

        try
        {
            if (set != null && set.next())
            {
                onlineTime = set.getString("onlinetime");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return onlineTime;
    }

    //===========================================================================================================================
    //ServerInfo
    //===========================================================================================================================
    public void UpdateMembersInServers(long guildid, String memberString)
    {
        LiteSQL.onUpdate("UPDATE servers SET members = '" + memberString + "' WHERE guildid = " + guildid);
    }

    public void UpdateDatesInServers(long guildid, String dateString)
    {
        LiteSQL.onUpdate("UPDATE servers SET dates = '" + dateString + "' WHERE guildid = " + guildid);
    }

    public String GetMembersInServer(long guildid)
    {
        String membersString = "";
        ResultSet set = LiteSQL.onQuery("SELECT * FROM servers WHERE guildid = " + guildid);

        try
        {
            if (set != null && set.next())
            {
                membersString = set.getString("members");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return membersString;
    }

    public String GetDatesInServer(long guildid)
    {
        String datesString = "";
        ResultSet set = LiteSQL.onQuery("SELECT * FROM servers WHERE guildid = " + guildid);

        try
        {
            if (set != null && set.next())
            {
                datesString = set.getString("dates");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return datesString;
    }

}
