package main.de.confusingbot.listener.botlistener;

import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void AddGuildToSQL(long guildid, String name) {
        LiteSQL.onUpdate("INSERT INTO servers(guildid, name) VALUES(" +
                guildid + ", '" + name + "')");
    }

    public void RemoveGuildFromSQL(long guildid) {
        for (String name : SQLManager.tabelNames) {
            LiteSQL.onUpdate("DELETE FROM " + name + " WHERE "
                    + "guildid = " + guildid);
        }
    }

}
