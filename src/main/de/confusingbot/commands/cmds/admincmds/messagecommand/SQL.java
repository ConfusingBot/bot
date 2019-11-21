package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================

    public void MessageAddToSQL(long guildid, long channelid, String hexColor, String messagetype, String title, String message)
    {
        LiteSQL.onUpdate("INSERT INTO messagecommand(guildid, channelid, color, messagetype, title, message) VALUES(" +
                guildid + ", " + channelid + ", '" + hexColor + "', '" + messagetype + "', '" + title + "', '" + message + "')");
    }

    public void MessageRemoveFromSQL(long guildid, String messagetype)
    {
        LiteSQL.onUpdate("DELETE FROM messagecommand WHERE "
                + "guildid = " + guildid
                + " AND messagetype = '" + messagetype + "'");
    }

    public boolean MessageExistsInSQL(long guildid, String messagetype)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public String GetMessageFormSQL(long guildid, String messagetype)
    {
        String message = "";

        return message;
    }

    public String GetTitleFromSQL(long guildid, String messagetype)
    {
        String title = "";

        return title;
    }

    public long GetChannelIDFormSQL(long guildid, String messagetype)
    {
        long id = -1;

        return id;
    }








}
