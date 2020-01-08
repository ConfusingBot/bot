package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

public class EventCommandListener
{
    public void onReactionAdd(MessageReactionAddEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (EventCommandManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();

                    long roleID = EventCommandManager.sql.getRoleID(guild.getIdLong(), messageid);

                    //add role
                    if (roleID != -1)
                        guild.addRoleToMember(event.getMember(), guild.getRoleById(roleID)).queue();
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        Member member = event.getMember();
        if (member != null)
        {
            if (event.getChannelType() == ChannelType.TEXT)
            {
                long messageid = event.getMessageIdLong();

                if (EventCommandManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
                {
                    if (!event.getUser().isBot())
                    {
                        Guild guild = event.getGuild();

                        long roleID = EventCommandManager.sql.getRoleID(guild.getIdLong(), messageid);

                        //remove role
                        if (roleID != -1)
                            guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleID)).queue();
                    }
                }
            }
        }
    }
}
