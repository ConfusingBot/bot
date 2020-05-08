package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.util.List;

public class VoteCommandListener
{
    //Needed Permissions
    Permission MESSAGE_MANAGE = Permission.MESSAGE_MANAGE;

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT && !event.getMember().getUser().isBot())
        {
            Guild guild = event.getGuild();
            Member bot = guild.getSelfMember();
            long messageid = event.getMessageIdLong();

            if (VoteCommandManager.sql.containsMessageID(guild.getIdLong(), messageid))
            {
                Member member = event.getMember();
                List<Long> allowedRoleIDs = VoteCommandManager.sql.getAllowedRoleIDs(guild.getIdLong(), messageid);
                boolean isAbleToReact = false;
                if (allowedRoleIDs.size() > 0)
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
                else
                {
                    isAbleToReact = true;
                }

                //Remove reaction if not able to react
                if (!isAbleToReact)
                {
                    if (bot.hasPermission(guild.getDefaultChannel(), MESSAGE_MANAGE))
                        event.getReaction().removeReaction(event.getUser()).queue();
                    else
                        EmbedManager.SendNoPermissionEmbed(event.getTextChannel(), MESSAGE_MANAGE, "VoteCommand | Can't remove reaction from Member!");
                }
            }
        }
    }
}
