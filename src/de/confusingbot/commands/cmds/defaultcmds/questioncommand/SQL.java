package de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import de.confusingbot.manage.sql.SQLManager;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void AddQuestionCategorieToSQL(long guildid, long categoryid)
    {
        SQLManager.onUpdate("INSERT INTO questioncategories(guildid, categoryid) VALUES (" +
                guildid + ", " + categoryid + ")");
    }

    public boolean ServerHasQuestionCategory(long guildid)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM questioncategories WHERE "
                + "guildid = " + guildid);

        try
        {
            if (set != null && set.next())
            {
                return true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void AddGeneratedQuestionToSQL(long guildID, long channelID, long memberID, String creationTime, String deleteTime)
    {

        SQLManager.onUpdate("INSERT INTO questioncommand(guildid, channelid, memberid, creationtime, deletetime) VALUES (" +
                guildID + ", " + channelID + ", " + memberID + ", '" + creationTime + "', '" + deleteTime + "')");
    }

    public void UpdateDeleteTimeInSQL(long guildID, long channelID, String newDeleteTime)
    {
        SQLManager.onUpdate("UPDATE questioncommand SET deletetime = '" + newDeleteTime + "' WHERE guildid = " + guildID + " AND channelid = " + channelID);
    }

    public Member GetQuestionAskMember(Guild guild, long channelid)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM questioncommand WHERE "
                + "guildid = " + guild.getIdLong()
                + " AND channelid = " + channelid);

        Member sentQuestionMemeber = null;
        try
        {
            if (set != null && set.next())
            {
                long memberid = set.getLong("memberid");
                sentQuestionMemeber = guild.getMemberById(memberid);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return sentQuestionMemeber;
    }

    public Category GetQuestionCategory(Guild guild)
    {
        Category category = null;
        ResultSet set = SQLManager.onQuery("SELECT * FROM questioncategories WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            if (set != null && set.next())
            {
                long questionCategoryID = set.getLong("categoryid");
                category = guild.getCategoryById(questionCategoryID);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return category;
    }

    public void RemoveQuestionCategoryFromSQL(long guildid)
    {
        SQLManager.onUpdate("DELETE FROM questioncategories WHERE "
                + "guildid = " + guildid);
    }

    public void removeQuestionFromSQL(long guildid, long channelid, long memberid)
    {
        SQLManager.onUpdate("DELETE FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND memberid = " + memberid);
    }

    public String getQuestionDeleteTime(long guildid, long channelid)
    {
        String deleteTime = "";

        ResultSet set = SQLManager.onQuery("SELECT * FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid);

        try
        {
            if (set != null && set.next())
            {
                deleteTime = set.getString("deletetime");

            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return deleteTime;
    }

    public String getQuestionCreationTime(long guildid, long channelid)
    {
        String creationTime = "";

        ResultSet set = SQLManager.onQuery("SELECT * FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid);

        try
        {
            if (set != null && set.next())
            {
                creationTime = set.getString("creationtime");

            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return creationTime;
    }
}

