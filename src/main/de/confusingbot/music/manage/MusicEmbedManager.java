package main.de.confusingbot.music.manage;

import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.music.MusicEmbeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class MusicEmbedManager
{

    MusicController controller;
    Guild guild;
    MusicEmbeds embeds;

    public MusicEmbedManager(MusicController controller)
    {
        this.controller = controller;
        this.guild = controller.getGuild();
        embeds = new MusicEmbeds(this);
    }

    public void sendMusicEmbed(EmbedBuilder builder, int time)
    {
        TextChannel channel = getLastUsedChannel();

        if (guild != null)
        {
            if (channel != null)
            {
                if (time != 0)
                {
                    EmbedManager.SendEmbed(builder, channel, time);
                }
                else
                {
                    EmbedManager.SendEmbed(builder, channel, 0);
                }
            }
        }

    }

    public MusicEmbeds getMusicEmbed()
    {
        return embeds;
    }

    //=====================================================================================================================================
    //Delete
    //=====================================================================================================================================
    private long musicLastPlayMessageID = -1;
    private long pauseLastMessageID = -1;

    public void DeleteLastSongEmbed()
    {
        if (musicLastPlayMessageID != -1)
        {
            TextChannel channel = getLastUsedChannel();

            EmbedManager.DeleteMessageByID(channel, musicLastPlayMessageID);
        }
    }

    public void DeletePauseSongEmbed()
    {
        if (pauseLastMessageID != -1)
        {
            TextChannel channel = getLastUsedChannel();

            EmbedManager.DeleteMessageByID(channel, pauseLastMessageID);
        }
    }

    public void setMusicLastPlayMessageID(long musicLastPlayMessageID)
    {
        this.musicLastPlayMessageID = musicLastPlayMessageID;
    }

    public void setPauseLastMessageID(long pauseLastMessageID)
    {
        this.pauseLastMessageID = pauseLastMessageID;
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    public TextChannel getLastUsedChannel()
    {
        return guild.getTextChannelById(controller.getLastUsedChannelId());
    }
}
