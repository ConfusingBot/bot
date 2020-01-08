package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRoleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
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
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (!eventNames.isEmpty() || eventNames.size() > 0)
        {
            //TODO is only a test
            StringBuilder builder = new StringBuilder();
            for (String eventName : eventNames)
            {
                builder.append(eventName + "\n");
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
}
