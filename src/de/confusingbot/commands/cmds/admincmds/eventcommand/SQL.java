package de.confusingbot.commands.cmds.admincmds.eventcommand;

import de.confusingbot.manage.sql.SQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{

    public void addToSQL(long guildid, long channelid, long messageid, long roleid, String hexColor, String emoteString, String eventName, String endTime, String creationTime)
    {
        SQLManager.onUpdate("INSERT INTO event(guildid, channelid, messageid, roleid, color, emote, name, endtime, creationtime) VALUES (" +
                guildid + ", " + channelid + ", " + messageid + ", " + roleid
                + ", '" + hexColor + "', '" + emoteString + "', '" + eventName + "', '" + endTime + "', '" + creationTime + "')");
    }

    public void removeFormSQL(long guildid, long roleid)
    {
        SQLManager.onUpdate("DELETE FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);
    }

    public boolean existsInSQL(long guildid, long roleid)
    {
        try
        {
            ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                    + "guildid = " + guildid
                    + " AND roleid = " + roleid);

            if (set != null && set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public boolean containsMessageID(long guildid, long messageID)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND messageid = " + messageID);
        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    if (messageID == set.getLong("messageid")) return true;
                }
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND emote = '" + emote + "'"
                + " AND messageid = " + messageID);
        try
        {
            if (set != null && set.next())
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set != null && set.next())
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set != null && set.next())
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set != null && set.next())
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleID);
        try
        {
            if (set != null && set.next())
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
        ResultSet set = SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + messageID);
        try
        {
            if (set != null && set.next())
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

        return SQLManager.onQuery("SELECT * FROM event WHERE "
                + "guildid = " + guildid);
    }

    public void removeFromSQL(long guildid, long id)
    {
        SQLManager.onUpdate("DELETE FROM event WHERE "
                + "id = " + id
                + " AND guildid = " + guildid);
    }
}
