package main.de.confusingbot.listener;

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

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        ResultSet set = LiteSQL.onQuery("SELECT * FROM acceptrules WHERE "
                + "guildid = " + event.getGuild().getIdLong());
        try {
            if (set.next()) {
                long rolenotacceptedid = set.getLong("rolenotacceptedid");
                long channelid = set.getLong("channelid");

                Member member = event.getMember();
                TextChannel channel = event.getGuild().getDefaultChannel();
                TextChannel rules = event.getGuild().getTextChannelById(channelid);
                Guild guild = event.getGuild();

                sendDefaultWelcomeMessage(channel, rules, member);

                addMemberRole(guild, member, rolenotacceptedid);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //TODO create a default joinRole command if the user won't create a rule
    }

    //TODO create custom Welcome Message
    private void sendDefaultWelcomeMessage(TextChannel channel, TextChannel rules, Member member) {
        if (channel != null) {

            EmbedManager.SendCustomEmbed("\uD83C\uDF89", "**Welcome** " + member.getAsMention() +
                    "\n\n**Please accept the**" + rules.getAsMention() + "\n" +
                    "**and enjoy your time** \uD83C\uDFDD", Color.ORANGE, channel, 0);
        }
    }

    private void addMemberRole(Guild guild, Member member, long roleid) {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }

}
