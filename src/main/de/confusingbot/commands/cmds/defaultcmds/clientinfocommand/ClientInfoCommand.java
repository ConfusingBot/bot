package main.de.confusingbot.commands.cmds.defaultcmds.clientinfocommand;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import main.de.confusingbot.Main;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;

public class ClientInfoCommand implements ServerCommand
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    String noInformationString = "none";

    Strings strings = new Strings();

    @Override
    public void performCommand(Member requester, TextChannel channel, Message message)
    {
        message.delete().queue();

        List<Member> members = message.getMentionedMembers();

        if (members.size() > 0)
        {
            for (Member member : members)
            {
                EmbedManager.SendEmbed(InfoEmbed(requester, member, channel), channel, 30);
            }
        }
        else
        {
            //Usage
            strings.ClientInfoUsage(channel);
        }
    }

    public EmbedBuilder InfoEmbed(Member requester, Member member, TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Requested by " + requester.getUser().getName());

        builder.setColor(member.getColor());
        builder.setTimestamp(OffsetDateTime.now());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());

        builder.addField("**Member\uD83D\uDC68\u200D\uD83D\uDCBB:** ", member.getAsMention(), true);
        builder.addField("**Name\uD83D\uDC40:** ", member.getUser().getName(), true);
        builder.addField("**JoinDate\uD83C\uDF08:** ", displayJoinDate(member), true);
        builder.addField("**CreationDate\uD83D\uDCA5:** ", displayCreateDate(member), true);
        builder.addField("**Activity\uD83D\uDEB4:** ", displayActivityInfo(member), true);
        builder.addField("**Status\uD83D\uDD54:** ", member.getOnlineStatus().toString(), true);
        builder.addField("**Roles\uD83C\uDF20:** ", roles(member).toString(), true);

        return builder;
    }

    private String displayActivityInfo(Member member)
    {
        try
        {
            String activity = member.getActivities().get(0).getName();

            return activity;
        } catch (Exception e)
        {
            return noInformationString;
        }
    }

    private String displayJoinDate(Member member)
    {
        try
        {
            String joinedDate = formatter.format(member.getTimeJoined().toLocalDate());
            return joinedDate;
        } catch (Exception e)
        {
            return noInformationString;
        }
    }

    private String displayCreateDate(Member member)
    {
        try
        {
            String createdDate = formatter.format(member.getTimeCreated().toLocalDate());
            return createdDate;
        } catch (Exception e)
        {
            return noInformationString;
        }
    }

    private StringBuilder roles(Member member)
    {
        StringBuilder roleBuilder = new StringBuilder();

        List<Role> roles = member.getRoles().stream().collect(Collectors.toList());
        List<Role> roleBorders = getRoleBorders(member.getGuild());

        for (Role role : roles)
        {
            boolean borderRole = false;
            for (Role roleBorder : roleBorders)
            {
                if (roleBorder.getIdLong() == role.getIdLong())
                {
                    roleBuilder.append("\n**" + role.getName() + "**\n");
                    borderRole = true;
                }
            }
            if (!borderRole)
                roleBuilder.append("" + role.getAsMention() + "  ");
        }
        return roleBuilder;
    }

    private List<Role> getRoleBorders(Guild guild)
    {
        List<Role> roleBorders = new ArrayList<>();

        ResultSet set = LiteSQL.onQuery("SELECT * FROM roleborders WHERE "
                + "guildid = " + guild.getIdLong());

        try
        {
            while (set.next())
            {
                Role role = guild.getRoleById(set.getLong("roleid"));
                roleBorders.add(role);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roleBorders;
    }

}
