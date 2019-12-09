package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================
    public void addToSQL(long guildid, long channelid, String info, int time) {
        LiteSQL.onUpdate("INSERT INTO repeatinfo(guildid, channelid, info, time) VALUES(" +
                guildid + ", " + channelid + ", '" + info + "', " + time + ")");
    }

    public void removeFormSQL(long guildid, int id) {
        LiteSQL.onUpdate("DELETE FROM repeatinfo WHERE "
                + "guildid = " + guildid
                + "id = " + id);
    }

    public List<Integer> getRepeatInfoIDs(long guildid) {
        List<Integer> list = new ArrayList<>();

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid);

            while (set.next()) {
                list.add(set.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getRepeatInfoByID(long guildid, int id) {
        String repeatInfoString = "error";

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                    + "guildid = " + guildid
                    + "AND id = " + id);

            while (set.next()) {
                repeatInfoString = set.getString("info");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return repeatInfoString;
    }

    public ResultSet GetRepeatInfoResultSet(long guildid) {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo WHERE "
                + "guildid = " + guildid);

        return set;
    }
}
