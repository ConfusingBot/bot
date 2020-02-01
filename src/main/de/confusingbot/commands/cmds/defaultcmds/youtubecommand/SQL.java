package main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

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
    public boolean ExistsInSQL(long guildid, String youtubechannelid)
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM youtubeannouncement WHERE "
                    + "guildid = " + guildid
                    + " AND youtubechannelid = '" + youtubechannelid + "'");

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long channelid, String youtubechannelid, String description, String roleids)
    {
        LiteSQL.onUpdate("INSERT INTO youtubeannouncement(guildid, channelid, youtubechannelid, description, roleids) VALUES(" +
                guildid + ", " + channelid + ", '" + youtubechannelid + "', '" + description + "', '" + roleids + "')");
    }

    public void removeFormSQL(long guildid, String youtubechannelid)
    {
        LiteSQL.onUpdate("DELETE FROM youtubeannouncement WHERE "
                + "guildid = " + guildid
                + " AND youtubechannelid = '" + youtubechannelid + "'");
    }

    public void UpdateRoleIdsInSQL(long guildid, String channelId, String newRoleIdString)
    {
        LiteSQL.onUpdate("UPDATE youtubeannouncment SET roleids = '" + newRoleIdString + "' WHERE guildid = " + guildid + " AND youtubechannelid = '" + channelId + "'");
    }

    public List<String> getYoutubeAnnouncements(long guildid)
    {
        List<String> youtubechannelid = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM youtubeannouncement WHERE "
                + "guildid = " + guildid);

        try
        {
            while (set.next())
            {
                youtubechannelid.add(set.getString("youtubechannelid"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return youtubechannelid;
    }
}
