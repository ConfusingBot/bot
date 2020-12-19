package de.confusingbot.commands.cmds.admincmds.joinrole;

import de.confusingbot.manage.sql.LiteSQL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public boolean ExistsInSQL(long guildid, long roleid)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE "
                    + "guildid = " + guildid
                    + " AND roleid = " + roleid);

            if (set != null && set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long roleid)
    {
        LiteSQL.onUpdate("INSERT INTO joinrole(guildid, roleid) VALUES (" +
                guildid + ", " + roleid + ")");
    }

    public void removeFormSQL(long guildid, long roleid)
    {
        LiteSQL.onUpdate("DELETE FROM joinrole WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);
    }

    public List<Long> getRoleIDs(long guildid)
    {
        List<Long> roleids = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM joinrole WHERE "
                + "guildid = " + guildid);

        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    roleids.add(set.getLong("roleid"));
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return roleids;
    }
}
