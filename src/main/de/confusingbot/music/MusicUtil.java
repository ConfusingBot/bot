package main.de.confusingbot.music;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicUtil {

    public static void updateChannel(TextChannel channel, Member member) {

        ResultSet set = LiteSQL.onQuery("SELECT * FROM musicchannel WHERE guildid = " + channel.getGuild().getIdLong());

        try {
            if (set.next()) {
                LiteSQL.onUpdate("UPDATE musicchannel SET channelid = " + channel.getIdLong() + " WHERE guildid = " + channel.getGuild().getIdLong());//https://www.sqlitetutorial.net/sqlite-update/
            } else {
                LiteSQL.onUpdate("INSERT INTO musicchannel(guildid, channelid, memberid) VALUES(" + channel.getGuild().getIdLong() + ", " + channel.getIdLong() + ", " + member.getIdLong() + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
