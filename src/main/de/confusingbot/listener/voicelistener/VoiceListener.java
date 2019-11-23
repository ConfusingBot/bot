package main.de.confusingbot.listener.voicelistener;

import main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand.TempVoiceChannelListener;
import main.de.confusingbot.music.UpdateVoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceListener extends ListenerAdapter
{

    TempVoiceChannelListener tempVoiceChannelListener = new TempVoiceChannelListener();

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event)
    {
        //TempChannels
        tempVoiceChannelListener.onJoin(event.getChannelJoined(), event.getEntity());
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event)
    {
        //TempChannels
        tempVoiceChannelListener.onLeave(event.getChannelLeft());

        //Player left MusicBot channel
        UpdateVoiceChannel.onPlayerLeave(event.getChannelLeft());
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event)
    {
        //TempChannels
        tempVoiceChannelListener.onLeave(event.getChannelLeft());
        tempVoiceChannelListener.onJoin(event.getChannelJoined(), event.getEntity());

    }


}
