package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public boolean hasEnabledCountOfInfos(long guildid, int enabledCount)
    {
        int infoCount = 0;
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid);

            while (set.next()) infoCount++;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return infoCount <= enabledCount;
    }

    public void addToSQL(long guildid, long channelid, String info, int time)
    {
        LiteSQL.onUpdate("INSERT INTO repeatinfo(guildid, channelid, info, time) VALUES(" +
                guildid + ", " + channelid + ", '" + info + "', " + time + ")");
    }

    public void removeFormSQL(long guildid, int index)
    {
        int id = getInfoIdFromIndex(guildid, index);
        LiteSQL.onUpdate("DELETE FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + "id = " + id);
    }

    public int getInfoIdFromIndex(long guildid, int index)
    {
        int id = -1;
        int infoCount = 1;
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid);

            while (set.next())
            {
                if (infoCount == index)
                {
                    id = set.getInt("id");
                    return id;
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return id;
    }
}
