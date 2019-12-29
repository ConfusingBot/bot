package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.List;

public class VoteCommandListener
{

    public void onReactionAdd(MessageReactionAddEvent event)
    {

        if (event.getChannelType() == ChannelType.TEXT)
        {
            Guild guild = event.getGuild();
            long messageid = event.getMessageIdLong();

            if (VoteCommandManager.sql.containsMessageID(guild.getIdLong(), messageid))
            {
                Member member = event.getMember();
                List<Long> allowedRoleIDs = VoteCommandManager.sql.getAllowedRoleIDs(guild.getIdLong(), messageid);
                boolean isAbleToReact = false;
                if (!allowedRoleIDs.isEmpty())
                {
                    for (Role role : member.getRoles())
                    {
                        for (Long id : allowedRoleIDs)
                        {
                            if (role.getIdLong() == id)
                            {
                                isAbleToReact = true;
                                break;
                            }
                        }

                    }
                }

                //Remove reaction if not able to react
                if (!isAbleToReact)
                {
                    event.getReaction().removeReaction(event.getUser()).queue();
                }
            }
        }
    }
}
