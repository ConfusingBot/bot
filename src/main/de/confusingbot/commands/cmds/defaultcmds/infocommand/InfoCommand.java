package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;

public class InfoCommand implements ServerCommand
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    String noInformationString = "none";
    LocalDateTime fromDateTime = LocalDateTime.now();

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

    @Override
    public void performCommand(Member requester, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length > 1)
        {
            switch (args[1])
            {
                case "bot":
                    //Bot info
                    BotInfoCommand(channel, requester);
                    break;
                default:
                    //Client info
                    ClientInfoCommand(args, message, channel, requester);
                    break;
            }
        }
        else
        {
            embeds.InfoUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ClientInfoCommand(String[] args, Message message, TextChannel channel, Member requester)
    {
        if (args[1].startsWith("@"))
        {
            List<Member> members = message.getMentionedMembers();
            if (members.size() > 0)
            {
                for (Member member : members)
                {
                    //Client info
                    embeds.SendInfoUserEmbed(channel, requester, member, displayJoinDate(member), displayCreateDate(member), displayActivityInfo(member), getRoles(member).toString());
                }
            }
            else
            {
                //Usage
                embeds.CouldNotFindMentionedMember(channel, args[1]);
            }
        }
        else
        {
            embeds.InfoUsage(channel);
        }
    }

    private void BotInfoCommand(TextChannel channel, Member requester)
    {
        embeds.SendInfoBotEmbed(channel, requester, displayOnlineTime(fromDateTime), fromDateTime.format(formatter), Main.version
                , "BennoDev#9351"
                , Main.INSTANCE.shardManager.getShards().get(0).getSelfUser().getEffectiveAvatarUrl()
                , getTotalServers(), getTotalMembers(), getTotalChannels());
    }

    //=====================================================================================================================================
    //Helper Client
    //=====================================================================================================================================
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

    private StringBuilder getRoles(Member member)
    {
        StringBuilder roleBuilder = new StringBuilder();

        List<Role> roles = member.getRoles().stream().collect(Collectors.toList());
        List<Role> roleBorders = sql.getRoleBorders(member.getGuild());

        for (Role role : roles)
        {
            boolean borderRole = false;
            for (Role roleBorder : roleBorders)
            {
                if (roleBorder.getIdLong() == role.getIdLong())
                {
                    roleBuilder.append("\n\n**" + role.getName() + "**\n");
                    borderRole = true;
                }
            }
            if (!borderRole)
                roleBuilder.append("" + role.getAsMention() + "  ");
        }
        return roleBuilder;
    }

    //=====================================================================================================================================
    //Helper Bot
    //=====================================================================================================================================

    private String displayOnlineTime(LocalDateTime fromDateTime)
    {
        LocalDateTime toDateTime = LocalDateTime.now();

        try
        {
            String onlineTime = "";

            LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

            long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
            tempDateTime = tempDateTime.plusYears(years);

            long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
            tempDateTime = tempDateTime.plusMonths(months);

            long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(days);


            long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(hours);

            long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
            tempDateTime = tempDateTime.plusMinutes(minutes);

            long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

            if (years > 0)
                onlineTime += years + " y ";

            if (months > 0)
                onlineTime += months + " m ";

            if (days > 0)
                onlineTime += days + " d ";

            if (hours > 0)
                onlineTime += hours + " h ";

            if (minutes > 0)
                onlineTime += minutes + " m ";

            if (seconds > 0)
                onlineTime += seconds + " s";


            return onlineTime;
        } catch (Exception e)
        {
            return noInformationString;
        }
    }

    private String getTotalServers()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            total.set(jda.getGuilds().size());

        });

        if (total == null)
        {
            return noInformationString;
        }
        else
        {
            return total.toString();
        }
    }

    private String getTotalChannels()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            int t = 0;
            List<Guild> guilds = jda.getGuilds();

            for (Guild guild : guilds)
            {
                t += guild.getChannels().size();
            }

            total.set(t);

        });

        if (total == null)
        {
            return noInformationString;
        }
        else
        {
            return total.toString();
        }
    }

    private String getTotalMembers()
    {
        AtomicInteger total = new AtomicInteger();
        Main.INSTANCE.shardManager.getShards().forEach(jda -> {

            int t = 0;
            List<Guild> guilds = jda.getGuilds();

            for (Guild guild : guilds)
            {
                t += guild.getMembers().size();
            }

            total.set(t);
        });

        if (total == null)
        {
            return noInformationString;
        }
        else
        {
            return total.toString();
        }
    }

}
