package main.de.confusingbot.listener.joinlistener;

import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    //SQL
    private ResultSet getResultSet(long guildid) {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid);

        return set;
    }

    public long getRoleNotAcceptedID(long guildid) {
        long rolenotacceptedid = -1;
        ResultSet set = getResultSet(guildid);
        try {
            if (set.next()) {
                rolenotacceptedid = set.getLong("rolenotacceptedid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rolenotacceptedid;
    }

    public long getChannelID(long guildid) {
        long channelid = -1;
        ResultSet set = getResultSet(guildid);
        try {
            if (set.next()) {
                channelid = set.getLong("channelid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return channelid;
    }

}
