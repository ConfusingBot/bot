package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelid, long messageid, String emoteString, long roleid)
    {
        LiteSQL.onUpdate("INSERT INTO reactroles(guildid, channelid, messageid, emote, roleid) VALUES (" +
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

    public long GetRoleIdFromSQL(long guildid, long channelid, long messageid, String emote)
    {
        long roleid = -1;

        ResultSet set = LiteSQL.onQuery("SELECT roleid FROM reactroles WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND messageid = " + messageid
                + " AND emote = '" + emote + "'");

        try
        {
            if (set.next())
            {
                roleid = set.getLong("roleid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleid;
    }

    public boolean containsMessageID(long guildid, long messageID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM reactroles WHERE "
                + "guildid = " + guildid);
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

    public ResultSet GetReactRolesResultSet(long guildid){
        ResultSet set = LiteSQL.onQuery("SELECT * FROM reactroles WHERE "
                + "guildid = " + guildid);

        return set;
    }


}
