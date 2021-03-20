package de.confusingbot.music.manage;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import de.confusingbot.manage.sql.SQLManager;
import de.confusingbot.music.AudioPlayerSendHandler;
import de.confusingbot.music.queue.Queue;
import de.confusingbot.music.TrackScheduler;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicController
{

    private Guild guild;
    private AudioPlayer player;
    private Queue queue;
    private MusicEmbedManager embedManager;

    private boolean isPaused = false;
    public long channelID;


    public MusicController(Guild guild)
    {
        this.guild = guild;
        this.player = Music.audioPlayerManager.createPlayer();
        this.queue = new Queue(this);
        this.embedManager = new MusicEmbedManager(this);

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        this.player.addListener(new TrackScheduler());
        this.player.setVolume(10);
    }

    public long getLastUsedMemberId()
    {
        long lastUsedMemberId = -1;

        try
        {
            ResultSet set = SQLManager.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guild.getIdLong());
            if (set.next())
            {
                lastUsedMemberId = set.getLong("memberid");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return lastUsedMemberId;
    }

    public long getLastUsedChannelId()
    {
        long channelid = -1;
        ResultSet set = SQLManager.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guild.getIdLong());

        try
        {
            if (set.next())
            {
                channelid = set.getLong("channelid");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return channelid;
    }

    public void updateChannel(TextChannel channel, Member member)
    {
        ResultSet set = SQLManager.onQuery("SELECT * FROM musicchannel WHERE guildid = " + channel.getGuild().getIdLong());

        try
        {
            if (set.next())
            {
                SQLManager.onUpdate("UPDATE musicchannel SET channelid = " + channel.getIdLong() + " WHERE guildid = " + channel.getGuild().getIdLong());//https://www.sqlitetutorial.net/sqlite-update/
                SQLManager.onUpdate("UPDATE musicchannel SET memberid = " + member.getIdLong() + " WHERE guildid = " + channel.getGuild().getIdLong());
            }
            else
            {
                SQLManager.onUpdate("INSERT INTO musicchannel(guildid, channelid, memberid) VALUES (" + channel.getGuild().getIdLong() + ", " + channel.getIdLong() + ", " + member.getIdLong() + ")");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Getter Setter
    public Guild getGuild()
    {
        return guild;
    }

    public AudioPlayer getPlayer()
    {
        return player;
    }

    public Queue getQueue()
    {
        return queue;
    }

    public MusicEmbedManager getMusicEmbedManager()
    {
        return embedManager;
    }

    public void setPaused(boolean paused)
    {
        isPaused = paused;
    }

    public boolean getPaused()
    {
        return isPaused;
    }


}
