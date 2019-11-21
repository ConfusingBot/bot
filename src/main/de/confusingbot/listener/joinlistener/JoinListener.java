package main.de.confusingbot.listener.joinlistener;

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

    SQL sql = new SQL();
    Embeds embeds = new Embeds();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        long guildid = event.getGuild().getIdLong();
        long rolenotacceptedid = sql.getRoleNotAcceptedID(guildid);
        long channelid = sql.getChannelID(guildid);

        if (rolenotacceptedid != -1 && channelid != -1) {
            Member member = event.getMember();
            TextChannel channel = event.getGuild().getDefaultChannel();
            TextChannel rules = event.getGuild().getTextChannelById(channelid);
            Guild guild = event.getGuild();

            //essage
            embeds.SendDefaultWelcomeMessage(channel, rules, member);

            //Add member accept rolerole
            addMemberRole(guild, member, rolenotacceptedid);
        }
    }


    //TODO create a default joinRole command if the user won't create a rule
    //TODO create custom Welcome Message


    //Helper
    private void addMemberRole(Guild guild, Member member, long roleid) {
        guild.addRoleToMember(member, guild.getRoleById(roleid)).queue();
    }

}
