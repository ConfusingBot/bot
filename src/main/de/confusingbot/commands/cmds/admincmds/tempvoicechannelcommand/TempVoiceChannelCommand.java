package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.List;

public class TempVoiceChannelCommand implements ServerCommand
{

    private SQL sql = new SQL();
    private Embeds embeds = new Embeds();

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
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        List<Long> tempChannels = sql.getTempChannelsFromGuild(guild.getIdLong());
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
