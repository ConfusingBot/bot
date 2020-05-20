package main.de.confusingbot.commands.cmds.admincmds.rolebordercommand;

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
    public void addToSQL(long channelid, long roleid, String roleName)
    {
        LiteSQL.onUpdate("INSERT INTO roleborders(guildid, roleid, name) VALUES (" +
                channelid + ", " + roleid + ", '" + roleName + "')");
    }

    public void removeFromSQL(long guildid, long roleid)
    {
        LiteSQL.onUpdate("DELETE FROM roleborders WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);
    }

    public boolean ExistsInSQL(long guildid, long roleid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);

        try
        {
            if (set.next()) return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public List<Long> getRoleBordersFromSQL(long guildid)
    {
        List<Long> roles = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE guildid = " + guildid);
        try
        {
            while (set.next())
            {
                long roleid = set.getLong("roleid");
                roles.add(roleid);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roles;
    }
}
