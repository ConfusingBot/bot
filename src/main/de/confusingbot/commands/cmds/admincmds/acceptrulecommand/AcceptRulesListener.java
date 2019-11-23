package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.List;

public class AcceptRulesListener
{

    SQL sql = new SQL();

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();
                    long rolenotacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong());
                    long roleacceptedid = sql.getRoleAcceptedIDFormSQL(event.getGuild().getIdLong());

                    //add member and remove blocked
                    guild.addRoleToMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                    //add roleborder
                    List<Role> roleBorders = sql.getRoleBorders(guild);
                    if (roleBorders != null)
                    {
                        for (int i = 0; i < roleBorders.size(); i++)
                        {
                            guild.addRoleToMember(event.getMember(), roleBorders.get(i)).queue();
                        }
                    }
                }
            }
        }
    }

    public void onReactionRemove(MessageReactionRemoveEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();
                    long rolenotacceptedid = sql.getRoleNotAcceptedIDFormSQL(event.getGuild().getIdLong());
                    long roleacceptedid = sql.getRoleAcceptedIDFormSQL(event.getGuild().getIdLong());

                    //add blocked and remove member
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                    guild.addRoleToMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                    //remove roleborders
                    List<Role> roleBorders = sql.getRoleBorders(guild);
                    if (roleBorders != null)
                    {
                        for (int i = 0; i < roleBorders.size(); i++)
                        {
                            guild.removeRoleFromMember(event.getMember(), roleBorders.get(i)).queue();
                        }
                    }
                }
            }
        }
    }

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        long rolenotacceptedid = sql.getRoleNotAcceptedIDFormSQL(guildid);

        if (rolenotacceptedid != -1)
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();

            //Add member accept rolerole
            addMemberRole(guild, member, rolenotacceptedid);
        }
    }

    //Helper
    private void addMemberRole(Guild guild, Member member, long roleid)
    {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }
}
