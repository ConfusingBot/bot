package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class TempVoiceChannelCommand implements ServerCommand
{

    private SQL sql = TempVoiceChannelCommandManager.sql;
    private Embeds embeds = TempVoiceChannelCommandManager.embeds;

    public TempVoiceChannelCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        // - tempchannel [add/remove]

        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (member.hasPermission(channel, TempVoiceChannelCommandManager.permission))
        {

            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "add":
                        AddCommand(args, member, guild, channel);
                        break;
                    case "remove":
                        RemoveCommand(args, member, guild, channel);
                        break;
                    case "list":
                        ListCommand(guild, channel);
                        break;
                    default:
                        //Usage
                        embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel, TempVoiceChannelCommandManager.permission);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        List<Long> tempVoiceChannelIds = sql.getTempChannelsFromSQL(guild.getIdLong());
        if (tempVoiceChannelIds.size() != 0 && tempVoiceChannelIds != null)
        {
            //Create Description -> all voice channel
            String description = "";
            for (long tempVoiceChannelID : tempVoiceChannelIds)
            {
                VoiceChannel tempChannel = guild.getVoiceChannelById(tempVoiceChannelID);
                if (tempChannel != null)
                {
                    description += "\uD83D\uDCCC " + tempChannel.getName() + "\n";
                }
                else
                {
                    description += "\uD83D\uDCCC **VoiceChannel does not exists!**\n";
                    //SQL
                    sql.removeFromSQL(tempVoiceChannelID, guild.getIdLong());
                }
            }

            //Message
            embeds.SendTempVoiceChannelListEmbed(channel, description);
        }
        else

        {
            embeds.HasNoTempChannelInformation(channel);
        }

    }

    private void AddCommand(String[] args, Member member, Guild guild, TextChannel channel)
    {
        if (args.length == 3)
        {
            String channelIdString = args[2];
            if (CommandsUtil.isNumeric(channelIdString))
            {
                long channelid = Long.parseLong(channelIdString);
                VoiceChannel voiceChannel = member.getGuild().getVoiceChannelById(channelid);
                if (voiceChannel != null)
                {
                    if (!sql.ExistInSQL(guild.getIdLong(), channelid))
                    {
                        //SQL
                        sql.addToSQL(channelid, guild.getIdLong());

                        //Message
                        embeds.SuccessfullyAddedTempchannel(channel, guild.getVoiceChannelById(channelid).getName());
                    }
                    else
                    {
                        //Error
                        embeds.VoiceChannelAlreadyExistsError(channel);
                    }
                }
                else
                {
                    //Error
                    embeds.CouldNotFindVoiceChannelByIDError(channel, channelid);
                }
            }
            else
            {
                //Error
                embeds.NoValidVoiceChannelIDNumberError(channel, channelIdString);
            }
        }
        else
        {
            //Usage
            embeds.AddUsage(channel);
        }
    }

    private void RemoveCommand(String[] args, Member member, Guild guild, TextChannel channel)
    {
        if (args.length == 3)
        {
            String channelIdString = args[2];
            if (CommandsUtil.isNumeric(channelIdString))
            {
                long channelid = Long.parseLong(channelIdString);
                VoiceChannel voiceChannel = member.getGuild().getVoiceChannelById(channelid);
                if (voiceChannel != null)
                {
                    if (sql.ExistInSQL(guild.getIdLong(), channelid))
                    {
                        //SQL
                        sql.removeFromSQL(channelid, guild.getIdLong());

                        //Message
                        embeds.SuccessfullyRemovedTempchannel(channel, guild.getVoiceChannelById(channelid).getName());
                    }
                    else
                    {
                        //Error
                        embeds.NoExistingTempChannelError(channel, channelid);
                    }
                }
                else
                {
                    //Error
                    embeds.CouldNotFindVoiceChannelByIDError(channel, channelid);
                }
            }
            else
            {
                //Error
                embeds.NoValidVoiceChannelIDNumberError(channel, channelIdString);
            }
        }
        else
        {
            //Usage
            embeds.RemoveUsage(channel);
        }
    }


}
