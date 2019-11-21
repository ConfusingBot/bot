package main.de.confusingbot.listener.voicelistener;

import main.de.confusingbot.channels.TempVoiceChannels;
import main.de.confusingbot.music.UpdateVoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event){

        //TempChannels
        TempVoiceChannels.onJoin(event.getChannelJoined(), event.getEntity());

    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event){

        //TempChannels
        TempVoiceChannels.onLeave(event.getChannelLeft());

        //Player left MusicBot channel
        UpdateVoiceChannel.onPlayerLeave(event.getChannelLeft());

    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event){

        //TempChannels
        TempVoiceChannels.onLeave(event.getChannelLeft());
        TempVoiceChannels.onJoin(event.getChannelJoined(), event.getEntity());

    }



}
