package main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
                    if (category != null)
                    {
                        if (member.getVoiceState().inVoiceChannel())
                        {
                            //create VoiceChannel
                            VoiceChannel voiceChannel = category.createVoiceChannel("⏳" + member.getEffectiveName() + "'s Channel").complete();
                            voiceChannel.getManager().setUserLimit(joinedChannel.getUserLimit()).queue();

                            //Callback for moveVoiceMember Queue
                            Consumer<? super Throwable> callback = (response) -> {
                                System.out.println("Failed to move Member!");
                            };

                            //Move member von placeholder Channel to his VoiceChannel
                            guild.moveVoiceMember(member, voiceChannel).queue(null, callback);

                            tempchannels.add(voiceChannel.getIdLong());
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendErrorEmbed("Make sure the TempVoiceChannel is in a category!", guild.getDefaultChannel(), 10);
                    }
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
        if (channel != null)
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
}
