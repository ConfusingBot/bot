package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{

    public void addToSQL(long guildid, long channelid, long messageid, long roleid, String hexColor, String emoteString, String eventName, String endTime, String creationTime)
    {
        LiteSQL.onUpdate("INSERT INTO event(guildid, channelid, messageid, roleid, color, emote, name, endtime, creationtime) VALUES(" +
                guildid + ", " + channelid + ", " + messageid + ", " + roleid
                + ", '" + hexColor + "', '" + emoteString + "', '" + eventName + "', '" + endTime + "', '" + creationTime + "')");
    }

    public void removeFormSQL(long guildid, long roleid)
    {
        LiteSQL.onUpdate("DELETE FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);
    }

    public boolean existsInSQL(long guildid, long roleid)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                    + "guildid = " + guildid
                    + " AND roleid = " + roleid);

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean containsMessageID(long guildid, long messageID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND messageid = " + messageID);
        try
        {
            while (set.next())
            {
                if (messageID == set.getLong("messageid")) return true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public long getRoleID(long guildid, long messageID, String emote)
    {
        long roleID = -1;
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND emote = '" + emote + "'"
                + " AND messageid = " + messageID);
        try
        {
            if (set.next())
            {
                roleID = set.getLong("roleid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleID;
    }

    public String getEventName(long guildid, long roleID)
    {
        String name = "";
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set.next())
            {
                name = set.getString("name");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return name;
    }

    public long getEventMessageId(long guildid, long roleID)
    {
        long messageId = -1;
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set.next())
            {
                messageId = set.getLong("messageid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageId;
    }

    public long getEventChannelId(long guildid, long roleID)
    {
        long channelId = -1;
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set.next())
            {
                channelId = set.getLong("channelid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return channelId;
    }

    public boolean eventRoleExist(long guildid, long roleID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set.next())
            {
               return true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public String getEventColor(long guildid, long messageID)
    {
        String color = "";
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + messageID);
        try
        {
            if (set.next())
            {
                color = set.getString("color");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return color;
    }

    public ResultSet GetResultSet(long guildid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid);

        return set;
    }

    public void removeFromSQL(long guildid, long id)
    {
        LiteSQL.onUpdate("DELETE FROM event WHERE "
                + "id = " + id
                + " AND guildid = " + guildid);
    }
}
