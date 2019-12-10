package main.de.confusingbot.manage.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLManager
{

    public static List<String> tabelNames = new ArrayList<>();

    public static void onCreate()
    {

        tabelNames.add("servers");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS servers(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "name TEXT)");

        tabelNames.add("reactroles");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS reactroles(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "messageid INTEGER, " +
                "emote VARCHAR, " +
                "roleid INTEGER)");

        tabelNames.add("musicchannel");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS musicchannel(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "memberid INTEGER)");

        tabelNames.add("questioncommand");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS questioncommand(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "memberid INTEGER, " +
                "creationtime TEXT)");

        tabelNames.add("questioncategories");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS questioncategories(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "categoryid INTEGER)");

        tabelNames.add("tempchannels");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS tempchannels(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER)");

        tabelNames.add("acceptrules");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS acceptrules(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "messageid INTEGER, " +
                "emote VARCHAR, " +
                "roleacceptedid INTEGER, " +
                "rolenotacceptedid INTEGER)");

        tabelNames.add("roleborders");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS roleborders(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "roleid INTEGER, " +
                "name TEXT)");


        tabelNames.add("repeatinfo");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS repeatinfo(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "time INTEGER, " +
                "color TEXT, " +
                "title TEXT, " +
                "info TEXT)");

        tabelNames.add("messagecommand");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS messagecommand(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "color TEXT, " +
                "messagetype TEXT, " +
                "title TEXT, " +
                "message TEXT)");

        tabelNames.add("votecommand");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS votecommand(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "messageid INTEGER, " +
                "endTime INTEGER, " +
                "creationtime TEXT, " +
                "emotes TEXT)");
    }
}
