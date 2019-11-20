package main.de.confusingbot.commands.cmds.admincmds.tempchannelcommand;

import main.de.confusingbot.channels.TempVoiceChannels;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.List;

public class TempChannelCommand implements ServerCommand
{

    private SQL sql = new SQL();
    private Strings strings = new Strings();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        // - tempchannel [add/remove]

        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {

            if (args.length == 1)
            {
                ListCommand(guild, channel);
            }
            else if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "add":
                        AddCommand(args, member, guild, channel);
                        break;
                    case "remove":
                        RemoveCommand(args, member, guild, channel);
                        break;
                    default:
                        //Usage
                        strings.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                strings.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            strings.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        List<Long> tempChannels = TempVoiceChannels.getTempChannelsFromGuild(guild.getIdLong());
        if (tempChannels.size() != 0 && tempChannels != null)
        {
            //Create Description -> all voice channel
            String description = "";
            for (long tempChannel : tempChannels)
            {
                description += "" + guild.getVoiceChannelById(tempChannel).getName() + "\n";
            }

            //create Embed
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.decode("#15d1cb"));
            builder.setTitle("⏳TempChannels: ");
            builder.setDescription(description);

            EmbedManager.SendEmbed(builder, channel, 10);
        }
        else
        {
            strings.HasNoTempChannelInformation(channel);
        }
    }

    private void AddCommand(String[] args, Member member, Guild guild, TextChannel channel)
    {
        try
        {
            long channelid = Long.parseLong(args[2]);
            VoiceChannel voiceChannel = member.getGuild().getVoiceChannelById(channelid);
            if (voiceChannel != null)
            {
                if (!sql.ExistInSQL(channelid, guild.getIdLong()))
                {
                    //SQL
                    sql.addToSQL(channelid, guild.getIdLong());

                    //Message
                    strings.SuccessfullyAddedTempchannel(channel, guild.getVoiceChannelById(channelid).getName());
                }
                else
                {
                    //Error
                    strings.VoiceChannelAlreadyExistsError(channel);
                }
            }
            else
            {
                //Error
                strings.CouldNotFindVoiceChannelByIDError(channel, channelid);
            }

        } catch (NumberFormatException e)
        {
            //Error
            strings.NoValidVoiceChannelIDNumberError(channel, args[2]);
        }

    }

    private void RemoveCommand(String[] args, Member member, Guild guild, TextChannel channel)
    {
        try
        {
            long channelid = Long.parseLong(args[2]);
            VoiceChannel voiceChannel = member.getGuild().getVoiceChannelById(channelid);
            if (voiceChannel != null)
            {
                if (sql.ExistInSQL(channelid, guild.getIdLong()))
                {
                    //SQL
                    sql.removeFromSQL(channelid, guild.getIdLong());

                    //Message
                    strings.SuccessfullyRemovedTempchannel(channel, guild.getVoiceChannelById(channelid).getName());
                }
                else
                {
                    //Error
                    strings.NoExistingTempChannelError(channel, channelid);
                }
            }
            else
            {
                //Error
                strings.CouldNotFindVoiceChannelByIDError(channel, channelid);
            }
        } catch (NumberFormatException e)
        {
            //Error
            strings.NoValidVoiceChannelIDNumberError(channel, args[2]);
        }
    }


}
