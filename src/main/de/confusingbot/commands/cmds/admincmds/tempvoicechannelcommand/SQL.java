package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

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
    public boolean ExistInSQL(long guildid, long channelID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM tempchannels WHERE " +
                "guildid = " + guildid
                + " AND channelid = " + channelID);

        try
        {
            if (set != null && set.next()) return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long channelID, long guildid)
    {
        LiteSQL.onUpdate("INSERT INTO tempchannels(guildid, channelid) VALUES (" +
                guildid + ", " + channelID + ")");
    }

    public void removeFromSQL(long channelID, long guildid)
    {
        LiteSQL.onUpdate("DELETE FROM tempchannels WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelID);
    }

    public List<Long> getTempChannelsFromSQL(long guildid)
    {
        List<Long> channels = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM tempchannels WHERE guildid = " + guildid);
        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    long channelId = set.getLong("channelid");
                    channels.add(channelId);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return channels;
    }
}
