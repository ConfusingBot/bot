package de.confusingbot.listener.botlistener;

import de.confusingbot.manage.sql.SQLManager;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void AddGuildToSQL(long guildid, String name)
    {
        //' will execute a near "s": syntax error
        name = name.replace("'", "");

        SQLManager.onUpdate("INSERT INTO servers(guildid, name) VALUES (" +
                guildid + ", '" + name + "')");
    }

    public void RemoveGuildFromSQL(long guildid)
    {
        for (String name : SQLManager.tabelNames)
        {
            SQLManager.onUpdate("DELETE FROM " + name + " WHERE "
                    + "guildid = " + guildid);
        }
    }
}
