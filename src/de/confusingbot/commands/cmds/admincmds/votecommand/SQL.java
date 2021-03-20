package de.confusingbot.commands.cmds.admincmds.votecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.manage.sql.SQLManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelID, long messageid, String title, String allowedroles, String emotes, String endtime, String creationtime)
    {
        SQLManager.onUpdate("INSERT INTO votecommand(guildid, channelid, messageid, title, allowedroles, emotes, endtime, creationtime) VALUES (" +
                guildid + ", " + channelID + ", " + messageid + ", '" + title + "', '" + allowedroles + "', '" + emotes + "', '" + endtime + "', '" + creationtime + "')");
    }

    public void removeFromSQL(long guildid, long id)
    {
        SQLManager.onUpdate("DELETE FROM votecommand WHERE "
                + "id = " + id
                + " AND guildid = " + guildid);
    }

    public boolean containsMessageID(long guildid, long messageID)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM votecommand WHERE "
                + "guildid = " + guildid);
        try
        {
            if(set != null)
            {
                while (set.next())
                {
                    if (messageID == set.getLong("messageid")) return true;
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public List<Long> getAllowedRoleIDs(long guildid, long messageID)
    {
        List<Long> ids = new ArrayList<>();

        ResultSet set = SQLManager.onQuery("SELECT * FROM votecommand WHERE "
                + "guildid = " + guildid
                + " AND messageid = " + messageID);
        try
        {
            if (set != null && set.next())
            {
                String codedString = set.getString("allowedroles");
                if (!codedString.equals(""))
                {
                    List<String> idStrings = CommandsUtil.encodeString(codedString, ", ");
                    for (String idString : idStrings)
                    {
                        if (!idString.equals(""))
                            ids.add(Long.parseLong(idString));
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return ids;
    }
}
