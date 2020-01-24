package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListEventsRunnable implements Runnable
{
    Guild guild;
    TextChannel channel;
    long messageid;

    public ListEventsRunnable(Guild guild, TextChannel channel, long messageid)
    {
        this.guild = guild;
        this.channel = channel;
        this.messageid = messageid;
    }

    @Override
    public void run()
    {
        List<Long> messageIDs = new ArrayList<>();
        List<Long> channelIDs = new ArrayList<>();
        List<String> emoteStrings = new ArrayList<>();
        List<Long> eventRoleIDs = new ArrayList<>();
        List<String> eventNames = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();
        List<String> creationTimes = new ArrayList<>();

        try
        {
            ResultSet set = EventCommandManager.sql.GetResultSet(guild.getIdLong());

            while (set.next())
            {
                eventRoleIDs.add(set.getLong("roleid"));
                messageIDs.add(set.getLong("messageid"));
                channelIDs.add(set.getLong("channelid"));
                emoteStrings.add(set.getString("emote"));
                eventNames.add(set.getString("name"));
                creationTimes.add(set.getString("creationtime"));
                endTimes.add(set.getString("endtime"));

                if (!eventNames.isEmpty() || eventNames.size() > 0)
                {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < eventNames.size(); i++)
                    {
                        long time = CommandsUtil.getTimeBetweenTwoDates(creationTimes.get(i), endTimes.get(i), true);
                        builder.append("**" + eventNames.get(i) + "** ⏰ " + time + "\n");
                    }

                    //Message
                    EventCommandManager.embeds.SendListEmbed(channel, builder.toString());
                }
                else
                {
                    //Information
                    EventCommandManager.embeds.HasNoEventsInformation(channel);
                }

                //Delete Wait Message
                channel.deleteMessageById(messageid).queue();



            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }


    }
}
