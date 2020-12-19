package de.confusingbot.listener.joinlistener;

import de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import de.confusingbot.commands.cmds.admincmds.joinrole.JoinRoleListener;
import de.confusingbot.commands.cmds.admincmds.messagecommand.MessageListener;
import de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesListener;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class JoinListener extends ListenerAdapter
{

    AcceptRulesListener acceptRulesListener = new AcceptRulesListener();
    MessageListener messageListener = new MessageListener();
    JoinRoleListener joinRoleListener = new JoinRoleListener();
    ReactRolesListener reactRolesListener = new ReactRolesListener();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        acceptRulesListener.onMemberJoinListener(event);
        messageListener.onMemberJoinListener(event);
        joinRoleListener.onMemberJoinListener(event);
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        messageListener.onMemberLeaveEvent(event);
        acceptRulesListener.onMemberLeaveListener(event);
        reactRolesListener.onMemberLeaveListener(event);
    }
}
