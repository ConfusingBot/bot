package main.de.confusingbot.commands.cmds.admincmds.tempchannelcommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public boolean ExistInSQL(long channelID, long guildid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM tempchannels WHERE " +
                "guildid = " + guildid
                + " AND channelid = " + channelID);

        try
        {
            if (!set.next()) return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long channelID, long guildid)
    {
        LiteSQL.onUpdate("INSERT INTO tempchannels(guildid, channelid) VALUES(" +
                guildid + ", " + channelID + ")");
    }

    public void removeFromSQL(long channelID, long guildid)
    {
        LiteSQL.onUpdate("DELETE FROM tempchannels WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelID);
    }
}
