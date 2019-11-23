package main.de.confusingbot.commands.cmds.defaultcmds.clientinfocommand;

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
    public List<Role> getRoleBorders(Guild guild)
    {
        List<Role> roleBorders = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            while (set.next())
            {
                Role role = guild.getRoleById(set.getLong("roleid"));
                roleBorders.add(role);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleBorders;
    }

}
