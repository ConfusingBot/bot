package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    public boolean ExistInSQL(long guildid, long roldeID)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM inviterole WHERE " +
                "guildid = " + guildid
                + " AND roleid = " + roldeID);

        try
        {
            if (set != null && set.next()) return true;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }


    public void AddRoleToSQL(long guildid, long roleid, int invitions)
    {
        LiteSQL.onUpdate("INSERT INTO inviterole(guildid, invitions, roleid) VALUES (" +
                guildid + ", " + invitions + ", " + roleid + ")");
    }

    public void RemoveRoleFromSQL(long guildid, long roleid)
    {
        LiteSQL.onUpdate("DELETE FROM inviterole WHERE "
                + "guildid = " + guildid
                + " AND roleid = " + roleid);
    }

    public List<Long> getRoleIdsFromSQL(long guildid)
    {
        List<Long> roles = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM inviterole WHERE guildid = " + guildid);
        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    long roleid = set.getLong("roleid");
                    roles.add(roleid);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roles;
    }

    public List<Integer> getInvitionsFromSQL(long guildid)
    {
        List<Integer> invitionCounts = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM inviterole WHERE guildid = " + guildid);
        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    int invitions = set.getInt("invitions");
                    invitionCounts.add(invitions);
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return invitionCounts;
    }
}
