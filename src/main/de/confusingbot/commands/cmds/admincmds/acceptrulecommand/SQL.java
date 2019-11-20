package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public boolean ExistsInSQL(long guildid)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM reactroles WHERE "
                    + "guildid = " + guildid);

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long channelid, long messageid, String emoteString, long notAcceptedRoleID, long acceptedRoleID)
    {
        LiteSQL.onUpdate("INSERT INTO acceptrules(guildid, channelid, messageid, emote, rolenotacceptedid, roleacceptedid) VALUES(" +
                guildid + ", " + channelid + ", " + messageid + ", '" + emoteString + "', "
                + notAcceptedRoleID + ", " + acceptedRoleID + ")");
    }

    public void removeFormSQL(long guildid){
        LiteSQL.onUpdate("DELETE FROM acceptrules WHERE "
                + "guildid = " + guildid);
    }

}
