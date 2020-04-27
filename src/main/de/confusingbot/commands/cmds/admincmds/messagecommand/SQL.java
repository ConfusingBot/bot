package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

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

    public void MessageAddToSQL(long guildid, long channelid, String hexColor, String messagetype, String title, String message, boolean isPrivate)
    {
        LiteSQL.onUpdate("INSERT INTO messagecommand(guildid, channelid, color, messagetype, title, message, isprivate) " +
                "VALUES(" + guildid + ", " + channelid + ", '" + hexColor + "', '" + messagetype + "', '" + title + "', '" + message + "', " + (isPrivate ? 1 : 0) + ")");
    }

    public void MessageRemoveFromSQL(long guildid, String messagetype, boolean isPrivate)
    {
        LiteSQL.onUpdate("DELETE FROM messagecommand WHERE "
                + "guildid = " + guildid
                + " AND messagetype = '" + messagetype + "'"
                + " AND isprivate = " +  (isPrivate ? 1 : 0));
    }

    public boolean MessageExistsInSQL(long guildid, String messagetype, boolean isPrivate)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'"
                    + " AND isprivate = " +  (isPrivate ? 1 : 0));

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String GetMessageFormSQL(long guildid, String messagetype, boolean isPrivate)
    {
        String message = "**Error**";

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'"
                    + " AND isprivate = " +  (isPrivate ? 1 : 0));

            if (set.next())
            {
                message = set.getString("message");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return message;
    }

    public String GetTitleFromSQL(long guildid, String messagetype, boolean isPrivate)
    {
        String title = "";

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'"
                    + " AND isprivate = " +  (isPrivate ? 1 : 0));

            if (set.next())
            {
                title = set.getString("title");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return title;
    }

    public long GetChannelIDFormSQL(long guildid, String messagetype, boolean isPrivate)
    {
        long id = -1;

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'"
                    + " AND isprivate = " +  (isPrivate ? 1 : 0));

            if (set.next())
            {
                id = set.getLong("channelid");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return id;
    }

    public String GetColorFromSQL(long guildid, String messagetype, boolean isPrivate)
    {
        String hexColor = "";

        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'"
                    + " AND isprivate = " +  (isPrivate ? 1 : 0));

            if (set.next())
            {
                hexColor = set.getString("color");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return hexColor;
    }

}
