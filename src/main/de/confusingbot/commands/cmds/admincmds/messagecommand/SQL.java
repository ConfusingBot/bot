package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.manage.sql.LiteSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQL {
    //=====================================================================================================================================
    //SQL
    //=====================================================================================================================================

    public void MessageAddToSQL(long guildid, long channelid, String hexColor, String messagetype, String title, String message, String mentionedChannel, String mentionedRoles) {
        LiteSQL.onUpdate("INSERT INTO messagecommand(guildid, channelid, color, messagetype, title, message, mentionedChannel, mentionedRoles) VALUES(" +
                guildid + ", " + channelid + ", '" + hexColor + "', '" + messagetype + "', '" + title + "', '" + message + "', '" + mentionedChannel + "', '" + mentionedRoles + "')");
    }

    public void MessageRemoveFromSQL(long guildid, String messagetype) {
        LiteSQL.onUpdate("DELETE FROM messagecommand WHERE "
                + "guildid = " + guildid
                + " AND messagetype = '" + messagetype + "'");
    }

    public boolean MessageExistsInSQL(long guildid, String messagetype) {
        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String GetMessageFormSQL(long guildid, String messagetype) {
        String message = "**Error**";

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                message = set.getString("message");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return message;
    }

    public String GetTitleFromSQL(long guildid, String messagetype) {
        String title = "";

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                title = set.getString("title");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return title;
    }

    public long GetChannelIDFormSQL(long guildid, String messagetype) {
        long id = -1;

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                id = set.getLong("channelid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

    public String GetColorFromSQL(long guildid, String messagetype) {
        String hexColor = "";

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                hexColor = set.getString("color");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hexColor;
    }

    public List<Long> GetMentionedChannelsFromSQL(long guildid, String messagetype) {
        String mentionedChannelsString = "";
        List<Long> mentionedChannels = new ArrayList<>();

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                mentionedChannelsString = set.getString("mentionedChannel");

                String[] mentionedChannelsStringArray = mentionedChannelsString.split(" ");

                for(String channel : mentionedChannelsStringArray){
                    mentionedChannels.add(Long.parseLong(channel));
                }
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return mentionedChannels;
    }

    public List<Long> GetMentionedRolesFromSQL(long guildid, String messagetype) {
        String mentionedRolesString = "";
        List<Long> mentionedRoles = new ArrayList<>();

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM messagecommand WHERE "
                    + "guildid = " + guildid
                    + " AND messagetype = '" + messagetype + "'");

            if (set.next()) {
                mentionedRolesString = set.getString("mentionedRoles");

                String[] mentionedRolesStringArray = mentionedRolesString.split(" ");

                for(String channel : mentionedRolesStringArray){
                    mentionedRoles.add(Long.parseLong(channel));
                }
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }

        return mentionedRoles;
    }

}
