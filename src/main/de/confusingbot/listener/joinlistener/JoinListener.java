package main.de.confusingbot.listener.joinlistener;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageListener;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;


public class JoinListener extends ListenerAdapter {

    AcceptRulesListener acceptRulesListener = new AcceptRulesListener();
    MessageListener messageListener = new MessageListener();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {


        acceptRulesListener.onMemberJoinListener(event);
        messageListener.onMemberJoinListener(event);

    }





}
