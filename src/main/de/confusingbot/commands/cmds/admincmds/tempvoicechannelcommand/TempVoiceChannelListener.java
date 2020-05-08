package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.ArrayList;
import java.util.List;

public class TempVoiceChannelListener
{

    static List<Long> tempchannels = new ArrayList<>();
    SQL sql = new SQL();

    //Needed Permission
    Permission MANAGE_CHANNEL = Permission.MANAGE_CHANNEL;
    Permission VOICE_MOVE_OTHERS = Permission.VOICE_MOVE_OTHERS;

    //Events
    public void onJoin(VoiceChannel joinedChannel, Member member)
    {
        Guild guild = joinedChannel.getGuild();
        Member bot = guild.getSelfMember();
        List<Long> channelJoinIds = sql.getTempChannelsFromSQL(guild.getIdLong());


        if (channelJoinIds != null && channelJoinIds.contains(joinedChannel.getIdLong()))
        {
            if (bot.hasPermission(MANAGE_CHANNEL))
            {
                if (bot.hasPermission(VOICE_MOVE_OTHERS))
                {
                    Category category = joinedChannel.getParent();

                    //create VoiceChannel
                    VoiceChannel voiceChannel = category.createVoiceChannel("⏳" + member.getEffectiveName() + "'s Channel").complete();
                    voiceChannel.getManager().setUserLimit(joinedChannel.getUserLimit()).queue();

                    //Move member von placeholder Channel to his VoiceChannel
                    guild.moveVoiceMember(member, voiceChannel).queue();

                    tempchannels.add(voiceChannel.getIdLong());
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), VOICE_MOVE_OTHERS, "TempVoiceChannelCommand | Can't move Member into VoiceChannel!");
                }
            }
            else
            {
                //Error
                EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_CHANNEL, "TempVoiceChannelCommand | Can't create a VoiceChannel!");
            }
        }
    }

    public void onLeave(VoiceChannel channel)
    {
        Guild guild = channel.getGuild();
        Member bot = guild.getSelfMember();


        if (channel.getMembers().size() <= 0)
        {
            if (tempchannels.contains(channel.getIdLong()))
            {
                if (bot.hasPermission(MANAGE_CHANNEL))
                {
                    tempchannels.remove(channel.getIdLong());
                    channel.delete().queue();
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(guild.getDefaultChannel(), MANAGE_CHANNEL, "TempVoiceChannelCommand | Can't delete TempVoiceChannel!");
                }
            }

        }
    }
}
