package main.de.confusingbot.listener.joinlistener;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import main.de.confusingbot.commands.cmds.admincmds.joinrole.JoinRoleListener;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageListener;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JoinListener extends ListenerAdapter
{

    AcceptRulesListener acceptRulesListener = new AcceptRulesListener();
    MessageListener messageListener = new MessageListener();
    JoinRoleListener joinRoleListener = new JoinRoleListener();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        acceptRulesListener.onMemberJoinListener(event);
        messageListener.onMemberJoinListener(event);
        joinRoleListener.onMemberJoinListener(event);
    }

    @Override
    public void onGuildMemberLeave(@Nonnull GuildMemberLeaveEvent event)
    {
        messageListener.onMemberLeaveEvent(event);
    }
}
