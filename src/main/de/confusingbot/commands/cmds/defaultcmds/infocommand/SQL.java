package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public List<Long> getRoleBorders(long guildID)
    {
        List<Long> roleBorderIDs = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guildID);

        try
        {
            while (set.next())
            {
                roleBorderIDs.add(set.getLong("roleid"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleBorderIDs;
    }

}
