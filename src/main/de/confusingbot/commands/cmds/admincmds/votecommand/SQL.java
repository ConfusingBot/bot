package main.de.confusingbot.commands.cmds.admincmds.votecommand;

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
    public void addToSQL(long guildid, long channelID, long messageid, String title, long endTime, String creationtime, String emotes)
    {
        LiteSQL.onUpdate("INSERT INTO votecommand(guildid, channelid, messageid, title, endTime, creationtime, emotes) VALUES(" +
                guildid + ", " + channelID + ", " + messageid + ", '" + title + "', " + endTime + ", '" + creationtime +  "', '" + emotes + "')");
    }

    public void removeFromSQL(long guildid, long id)
    {
        LiteSQL.onUpdate("DELETE FROM votecommand WHERE "
                + "id = " + id
                + " AND guildid = " + guildid);
    }
}
