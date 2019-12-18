package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;

import java.util.List;

public class AcceptRulesListener
{

    public void onReactionAdd(MessageReactionAddEvent event)
    {
        if (event.getChannelType() == ChannelType.TEXT)
        {
            long messageid = event.getMessageIdLong();

            if (AcceptRuleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();
                    long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                    long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                    //add member and remove blocked
                    guild.addRoleToMember(event.getMember(), guild.getRoleById(roleacceptedid)).queue();
                    guild.removeRoleFromMember(event.getMember(), guild.getRoleById(rolenotacceptedid)).queue();

                    //add roleborder
                    List<Role> roleBorders = AcceptRuleManager.sql.getRoleBorders(guild);
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

            if (AcceptRuleManager.sql.containsMessageID(event.getGuild().getIdLong(), messageid))
            {
                if (!event.getUser().isBot())
                {
                    Guild guild = event.getGuild();
                    Member member = event.getMember();
                    long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(event.getGuild().getIdLong());
                    long roleacceptedid = AcceptRuleManager.sql.getAcceptedRoleID(event.getGuild().getIdLong());

                    //add blocked and remove member
                    guild.removeRoleFromMember(member, guild.getRoleById(roleacceptedid)).queue();
                    guild.addRoleToMember(member, guild.getRoleById(rolenotacceptedid)).queue();

                    //remove roleborders
                    List<Role> roleBorders = AcceptRuleManager.sql.getRoleBorders(guild);
                    if (roleBorders != null)
                    {
                        for (int i = 0; i < roleBorders.size(); i++)
                        {
                            guild.removeRoleFromMember(member, roleBorders.get(i)).queue();
                        }
                    }
                }
            }
        }
    }

    public void onMemberJoinListener(GuildMemberJoinEvent event)
    {
        long guildid = event.getGuild().getIdLong();
        long rolenotacceptedid = AcceptRuleManager.sql.getNotAcceptedRoleID(guildid);

        if (rolenotacceptedid != -1)
        {
            Member member = event.getMember();
            Guild guild = event.getGuild();

            //Add member accept rolerole
            addMemberRole(guild, member, rolenotacceptedid);
        }
    }

    //TODO never tried
    public void onMemberLeaveListener(GuildMemberLeaveEvent event)
    {
        Guild guild = event.getGuild();
        long messageID = AcceptRuleManager.sql.getMessageID(guild.getIdLong());
        long channelID = AcceptRuleManager.sql.getChannelID(guild.getIdLong());

        if(messageID != -1 && channelID != -1){
            TextChannel channel = guild.getTextChannelById(channelID);
            Message message = CommandsUtil.getLatestesMessageByID(channel, messageID);

            if(message != null){
                List<MessageReaction> reactions = message.getReactions();

                for(MessageReaction reaction : reactions){
                    User user = reaction.getPrivateChannel().getUser();
                    if(user != null){
                        message.removeReaction((Emote) reaction.getReactionEmote(), user);
                    }
                }
            }

        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private void addMemberRole(Guild guild, Member member, long roleid)
    {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }
}
