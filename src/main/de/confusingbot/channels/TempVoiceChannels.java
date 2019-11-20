package main.de.confusingbot.channels;

import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TempVoiceChannels
{

    static List<Long> tempchannels = new ArrayList<>();

    //Events
    public static void onJoin(VoiceChannel joinedChannel, Member member)
    {
        Guild guild = joinedChannel.getGuild();
        List<Long> channelJoinIds = getTempChannelsFromGuild(guild.getIdLong());

        if (channelJoinIds != null && channelJoinIds.contains(joinedChannel.getIdLong()))
        {
            Category category = joinedChannel.getParent();

            //create VoiceChannel
            VoiceChannel voiceChannel = category.createVoiceChannel("⏳" + member.getEffectiveName() + "'s Channel").complete();
            voiceChannel.getManager().setUserLimit(joinedChannel.getUserLimit()).queue();

            //Move member von placeholder Channel to his VoiceChannel
            guild.moveVoiceMember(member, voiceChannel).queue();

            tempchannels.add(voiceChannel.getIdLong());
        }
    }

    public static void onLeave(VoiceChannel channel)
    {
        if (channel.getMembers().size() <= 0)
        {
            if (tempchannels.contains(channel.getIdLong()))
            {
                tempchannels.remove(channel.getIdLong());
                channel.delete().queue();
            }
        }
    }

    //SQL
    public static List<Long> getTempChannelsFromGuild(long guildid)
    {
        List<Long> channels = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM tempchannels WHERE guildid = " + guildid);
        try
        {
            while (set.next())
            {
                long channelId = set.getLong("channelid");
                channels.add(channelId);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return channels;
    }

}
