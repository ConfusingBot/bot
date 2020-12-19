package de.confusingbot.commands.cmds.defaultcmds.infocommand;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import de.confusingbot.Main;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.BotInfo;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.ClientInfo;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.ServerInfo;
import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class InfoCommand implements ServerCommand
{
    Embeds embeds = InfoCommandManager.embeds;

    BotInfo botInfo = InfoCommandManager.botInfo;
    ServerInfo serverInfo = InfoCommandManager.serverInfo;
    ClientInfo clientInfo = InfoCommandManager.clientInfo;

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public InfoCommand()
    {
        embeds.HelpEmbed();

        //SQL
        InfoCommandManager.sql.addBotToSQL();
    }

    @Override
    public void performCommand(Member requester, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length > 1)
            {
                switch (args[1])
                {
                    case "bot":
                        //Bot info
                        BotInfoCommand(channel, requester);
                        break;
                    case "server":
                        //Bot info
                        ServerInfoCommand(channel, requester);
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
                    embeds.SendInfoUserEmbed(channel,
                            requester,
                            member,
                            clientInfo.displayJoinDate(member),
                            clientInfo.displayCreateDate(member),
                            clientInfo.displayActivityInfo(member),
                            clientInfo.getRolesFromMemberAsString(member));
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
        embeds.SendInfoBotEmbed(channel,
                requester,
                botInfo.updateBotOnlineTime(Main.botStartTime, false),
                Main.botStartTime.format(InfoCommandManager.formatter),
                Main.version,
                Main.linesOfCode,
                "BennoDev#9351",
                Main.INSTANCE.shardManager.getShards().get(0).getSelfUser().getEffectiveAvatarUrl(),
                botInfo.getTotalServers(),
                botInfo.getTotalMembers(),
                botInfo.getTotalChannels());
    }


    private void ServerInfoCommand(TextChannel channel, Member requester)
    {
        //Send wait message
        long messageid = embeds.SendWaitMessage(channel);

        Guild guild = channel.getGuild();
        String memberString = InfoCommandManager.sql.GetMembersInServer(guild.getIdLong());
        String dateString = InfoCommandManager.sql.GetDatesInServer(guild.getIdLong());

        List<String> dates = new ArrayList<>();
        List<Integer> members = new ArrayList<>();
        if (dateString != null && !dateString.equals(""))
            dates = CommandsUtil.encodeString(dateString, ", ");

        if (memberString != null && !memberString.equals(""))
            members = CommandsUtil.encodeInteger(memberString, ", ");

        List<String> finalDates = dates;
        List<Integer> finalMembers = members;
        new Thread(new Runnable()
        {
            public void run()
            {
                File chartFile = null;
                if (finalDates.size() > 2 && finalMembers.size() > 2)
                {
                    //Generate Graph
                    chartFile = serverInfo.createChartFile(finalMembers, finalDates);
                }

                //Send file to Discord
                embeds.SendInfoServerEmbed(channel,
                        requester,
                        chartFile,
                        guild.getChannels().size(),
                        guild.getVoiceChannels().size(), guild.getMemberCount(),
                        serverInfo.getBots(guild),
                        guild.getEmotes().size(),
                        guild.getRoles().size(),
                        guild.getCategories().size(),
                        InfoCommandManager.formatter.format(guild.getTimeCreated()),
                        guild.getOwner());

                //Delete Wait Message
                if (messageid != -1)
                    channel.deleteMessageById(messageid).queue();
            }
        }).start();
    }
}

