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

    Member bot;

    //Needed Permissions
    Permission MANAGE_CHANNEL = Permission.MANAGE_CHANNEL;
    Permission VOICE_MOVE_OTHERS = Permission.VOICE_MOVE_OTHERS;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        // - tempchannel [add/remove]

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            Guild guild = channel.getGuild();
            String[] args = CommandsUtil.messageToArgs(message);

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
        if (bot.hasPermission(MANAGE_CHANNEL))
        {
            if (bot.hasPermission(VOICE_MOVE_OTHERS))
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
            else
            {
                //Error
                EmbedManager.SendNoPermissionEmbed(channel, VOICE_MOVE_OTHERS, "");
            }
        }
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_CHANNEL, "");
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
