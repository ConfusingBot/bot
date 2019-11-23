package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class SQL
{
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void AddQuestionCategorieToSQL(long guildid, long categoryid)
    {
        LiteSQL.onUpdate("INSERT INTO questioncategories(guildid, categoryid) VALUES(" +
                guildid + ", " + categoryid + ")");
    }

    public boolean ServerHasQuestionCategory(long guildid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM questioncategories WHERE "
                + "guildid = " + guildid);

        try
        {
            while (set.next())
            {
                return true;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public void AddGeneratedQuestionToSQL(Member member, TextChannel textChannel)
    {
        //SQLDatabase
        long channelID = textChannel.getIdLong();
        long guildID = textChannel.getGuild().getIdLong();
        long memberID = member.getIdLong();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String creationTime = textChannel.getTimeCreated().toLocalDateTime().format(formatter);

        LiteSQL.onUpdate("INSERT INTO questioncommand(guildid, channelid, memberid, creationtime) VALUES(" +
                guildID + ", " + channelID + ", " + memberID + ", '" + creationTime + "')");
    }

    public Member GetQuestionAskMember(Guild guild, long channelid)
    {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM questioncommand WHERE "
                + "guildid = " + guild.getIdLong()
                + " AND channelid = " + channelid);

        Member sentQuestionMemeber = null;
        try
        {
            if (set.next())
            {
                long memberid = set.getLong("memberid");
                sentQuestionMemeber = guild.getMemberById(memberid);
            }
            else
            {
                sentQuestionMemeber = null;
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
        ResultSet set = LiteSQL.onQuery("SELECT * FROM questioncategories WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            if (set.next())
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

    public void RemoveQuestionChannelFromSQL(long guildid, long channelid, long memberid)
    {
        LiteSQL.onUpdate("DELETE FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND memberid = " + memberid);
    }

    public void RemoveQuestionCategoryFromSQL(long guildid)
    {
        LiteSQL.onUpdate("DELETE FROM questioncategories WHERE "
                + "guildid = " + guildid);
    }

    public void removeQuestionFromSQL(long guildid, long channelid, long memberid)
    {
        LiteSQL.onUpdate("DELETE FROM questioncommand WHERE "
                + "guildid = " + guildid
                + " AND channelid = " + channelid
                + " AND memberid = " + memberid);
    }
}

