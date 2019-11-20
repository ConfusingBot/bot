package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelid, long messageid, String emoteString, long roleid)
    {
        LiteSQL.onUpdate("INSERT INTO reactroles(guildid, channelid, messageid, emote, roleid) VALUES(" +
                guildid + ", " + channelid + ", " + messageid + ", '" + emoteString + "', " + roleid + ")");
    }

    public void removeFromSQL(long guildid, long channelid, long messageid, String emoteString, long roleid)
    {
        LiteSQL.onUpdate("DELETE FROM reactroles WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND messageid = " + messageid
                + " AND emote = '" + emoteString + "'"
                + " AND roleid = " + roleid);
    }

    public boolean ExistsInSQL(long guildid, long messageid, String emoteString, long roleid)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM reactroles WHERE "
                    + "guildid = " + guildid
                    + " AND messageid = " + messageid
                    + " AND emote = '" + emoteString + "'"
                    + " AND roleid = " + roleid);

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
