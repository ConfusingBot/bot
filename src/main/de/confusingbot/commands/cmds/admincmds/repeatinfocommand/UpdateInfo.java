package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class UpdateInfo
{

    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM repeatinfo");
            while (set.next())
            {
                long guildID = set.getLong("guildid");
                long channelID = set.getLong("channelid");
                int time = set.getInt("time");

                //TODO create a state when member guild or channel is null
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null) return;
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null) return;


                //TODO check if you can send InfoEmbed
               //TODO send info embed
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
