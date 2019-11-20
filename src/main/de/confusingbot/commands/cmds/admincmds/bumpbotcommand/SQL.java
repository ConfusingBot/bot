package main.de.confusingbot.commands.cmds.admincmds.bumpbotcommand;

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
            ResultSet set = LiteSQL.onQuery("SELECT * FROM bumpcommand WHERE "
                    + "guildid = " + guildid);

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long channelid, String command)
    {
        LiteSQL.onUpdate("INSERT INTO bumpcommand(guildid, channelid, command) VALUES(" +
                guildid + ", " + channelid + ", '" + command + "')");
    }

    public void removeFormSQL(long guildid)
    {
        LiteSQL.onUpdate("DELETE FROM acceptrules WHERE "
                + "guildid = " + guildid);
    }
}
