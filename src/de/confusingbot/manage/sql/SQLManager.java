package de.confusingbot.manage.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager {

    private static Connection connection;
    private static Statement statement;

    public static void connect() {
        connection = null;
        String jdbcURL = "jdbc:" + System.getenv("DB_URI");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        System.out.println("DB_URI: " + jdbcURL);
        System.out.println("DB_USERNAME: " + username);
        System.out.println("DB_PASSWORD: " + password);

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(jdbcURL, username, password);

            System.out.println("Verbindung zur Datenbank hergestellt");

            statement = connection.createStatement();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Verbindung zur Datenbank getrennt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void onUpdate(String sql) {
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet onQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> tabelNames = new ArrayList<>();

    public static void onCreate() {
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS bot(id SERIAL, " +
                "onlinetime TEXT, " +
                "users TEXT)");

        tabelNames.add("servers");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS servers(id SERIAL, " +
                "guildid BIGINT, " +
                "name TEXT, " +
                "dates TEXT, " +
                "members TEXT)");

        tabelNames.add("reactroles");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS reactroles(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "messageid BIGINT, " +
                "emote VARCHAR, " +
                "roleid BIGINT)");

        tabelNames.add("musicchannel");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS musicchannel(id SERIAL,  " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "memberid BIGINT)");

        tabelNames.add("questioncommand");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS questioncommand(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "memberid BIGINT, " +
                "creationtime TEXT, " +
                "deletetime TEXT)");

        tabelNames.add("questioncategories");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS questioncategories(id SERIAL, " +
                "guildid BIGINT, " +
                "categoryid BIGINT)");

        tabelNames.add("tempchannels");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS tempchannels(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT)");

        tabelNames.add("acceptrules");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS acceptrules(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "messageid BIGINT, " +
                "emote VARCHAR, " +
                "roleacceptedid BIGINT, " +
                "rolenotacceptedid BIGINT)");

        tabelNames.add("roleborders");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS roleborders(id SERIAL, " +
                "guildid BIGINT, " +
                "roleid BIGINT, " +
                "name TEXT)");


        tabelNames.add("repeatinfo");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS repeatinfo(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "time BIGINT, " +
                "color TEXT, " +
                "title TEXT, " +
                "info TEXT)");

        tabelNames.add("messagecommand");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS messagecommand(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "color TEXT, " +
                "messagetype TEXT, " +
                "title TEXT, " +
                "message TEXT, " +
                "isprivate BIGINT)");

        tabelNames.add("votecommand");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS votecommand(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "messageid BIGINT, " +
                "title TEXT, " +
                "allowedroles TEXT, " +
                "emotes TEXT, " +
                "endtime TEXT, " +
                "creationtime TEXT)");

        tabelNames.add("joinrole");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS joinrole(id SERIAL, " +
                "guildid BIGINT, " +
                "roleid BIGINT)");

        tabelNames.add("event");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS event(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "messageid BIGINT, " +
                "roleid BIGINT, " +
                "color TEXT, " +
                "emote TEXT, " +
                "name TEXT, " +
                "endtime TEXT, " +
                "creationtime TEXT)");

        tabelNames.add("inviterole");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS inviterole(id SERIAL, " +
                "guildid BIGINT, " +
                "invitions BIGINT, " +
                "roleid BIGINT)");

        tabelNames.add("youtubeannouncement");
        SQLManager.onUpdate("CREATE TABLE IF NOT EXISTS youtubeannouncement(id SERIAL, " +
                "guildid BIGINT, " +
                "channelid BIGINT, " +
                "youtubechannelid TEXT, " +
                "description TEXT, " +
                "roleids TEXT)");
    }
}
