package main.de.confusingbot.manage.sql;

import java.util.ArrayList;
import java.util.List;

public class SQLManager
{

    public static List<String> tabelNames = new ArrayList<>();

    public static void onCreate()
    {

        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS bot(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "onlinetime TEXT, " +
                "users TEXT)");

        tabelNames.add("servers");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS servers(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "name TEXT, " +
                "dates TEXT, " +
                "members TEXT)");

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
                "creationtime TEXT, " +
                "deletetime TEXT)");

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
                "title TEXT, " +
                "allowedroles TEXT, " +
                "emotes TEXT, " +
                "endtime TEXT, " +
                "creationtime TEXT)");

        tabelNames.add("joinrole");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS joinrole(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "roleid INTEGER)");

        tabelNames.add("event");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS event(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "channelid INTEGER, " +
                "messageid INTEGER, " +
                "roleid INTEGER, " +
                "color TEXT, " +
                "emote TEXT, " +
                "name TEXT, " +
                "endtime TEXT, " +
                "creationtime TEXT)");

        tabelNames.add("inviterole");
        LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS inviterole(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "guildid INTEGER, " +
                "invitions INTEGER, " +
                "roleid INTEGER)");
    }
}
