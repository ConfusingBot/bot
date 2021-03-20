package de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public boolean ExistsInSQL(long guildid)
    {
        try
        {
            ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                    + "guildid = " + guildid);

            if (set.next()) return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void addToSQL(long guildid, long channelid, long messageid, String emoteString, long notAcceptedRoleID, long acceptedRoleID)
    {
        SQLManager.onUpdate("INSERT INTO acceptrules(guildid, channelid, messageid, emote, rolenotacceptedid, roleacceptedid) VALUES (" +
                guildid + ", " + channelid + ", " + messageid + ", '" + emoteString + "', "
                + notAcceptedRoleID + ", " + acceptedRoleID + ")");
    }

    public void removeFormSQL(long guildid)
    {
        SQLManager.onUpdate("DELETE FROM acceptrules WHERE "
                + "guildid = " + guildid);
    }

    public long getNotAcceptedRoleID(long guildid)
    {
        long rolenotacceptedid = -1;

        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid);

        try
        {
            if (set.next())
            {
                rolenotacceptedid = set.getLong("rolenotacceptedid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rolenotacceptedid;
    }

    public long getAcceptedRoleID(long guildid)
    {
        long rolenotacceptedid = -1;

        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid
        );
        try
        {
            if (set.next())
            {
                rolenotacceptedid = set.getLong("roleacceptedid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rolenotacceptedid;
    }

    public boolean containsMessageID(long guildid, long messageID)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
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

    public long getMessageID(long guildid)
    {
        long messageID = -1;
        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid);
        try
        {
            if (set.next())
            {
                messageID = set.getLong("messageid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return messageID;
    }

    public long getChannelID(long guildid)
    {
        long channelID = -1;
        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid);
        try
        {
            if (set.next())
            {
                channelID = set.getLong("channelid");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return channelID;
    }

    public String getEmote(long guildid)
    {
        String emote = "Error";
        ResultSet set = SQLManager.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + guildid);
        try
        {
            if (set.next())
            {
                emote = set.getString("emote");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return emote;
    }

    public List<Long> getRoleBorderIDs(Guild guild)
    {
        List<Long> roleBordersIds = new ArrayList<>();

        ResultSet set = SQLManager.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            while (set.next())
            {
                roleBordersIds.add(set.getLong("roleid"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        return roleBordersIds;
    }
}
