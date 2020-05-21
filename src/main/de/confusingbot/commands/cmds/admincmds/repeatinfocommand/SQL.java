package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelid, int time, String color, String title, String info)
    {
        LiteSQL.onUpdate("INSERT INTO repeatinfo(guildid, channelid, time, color, title, info) VALUES (" +
                guildid + ", " + channelid + ", " + time + ", '" + color + "', '" + title + "', '" + info + "')");
    }

    public void removeFormSQL(long guildid, int id)
    {
        LiteSQL.onUpdate("DELETE FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);
    }

    public List<Integer> getRepeatInfoIDs(long guildid)
    {
        List<Integer> list = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    list.add(set.getInt("id"));
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public int getRepeatInfoTimeByID(long guildid, int id)
    {
        int time = -1;

        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    time = set.getInt("time");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return time;
    }

    public String getRepeatInfoByID(long guildid, int id)
    {
        String repeatInfoString = "error";

        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    repeatInfoString = set.getString("info");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return repeatInfoString;
    }

    public String getRepeatInfoTitleById(long guildid, int id)
    {
        String title = "error";

        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    title = set.getString("title");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return title;
    }

    public long getRepeatInfoChannelIdById(long guildid, int id)
    {
        long channelId = -1;

        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + " AND id = " + id);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    channelId = set.getLong("channelid");
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return channelId;
    }

}
