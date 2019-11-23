package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InfoHandler
{


    public void loopInfos()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM bumpcommand");
            while (set.next())
            {
                long guildID = set.getLong("guildid");
                long channelID = set.getLong("channelid");

                //TODO create a state when member guild or channel is null
                Guild guild = Main.INSTANCE.shardManager.getGuildById(guildID);
                if (guild == null) return;
                TextChannel channel = guild.getTextChannelById(channelID);
                if (channel == null) return;

                //if((Infotime % currentTime) == 0) channel.sendMessage().queue();
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
