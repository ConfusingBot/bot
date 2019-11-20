package main.de.confusingbot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MusicController {

    private Guild guild;
    private AudioPlayer player;
    private Queue queue;
    private MusicEmbeds embeds;

    private boolean isPaused = false;


    public MusicController(Guild guild) {

        this.guild = guild;
        this.player = Music.audioPlayerManager.createPlayer();
        this.queue = new Queue(this);
        this.embeds = new MusicEmbeds(this);

        this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
        this.player.addListener(new TrackScheduler());
        this.player.setVolume(10);
    }

    public long getLastUsedUserId() {
        long lastUsedUserId = -1;

        try {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guild.getIdLong());
            if (set.next()) {
                lastUsedUserId = set.getLong("channelid");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastUsedUserId;
    }

    public long getLastUsedChannelId() {
        long channelid = -1;
        ResultSet set = LiteSQL.onQuery("SELECT * FROM musicchannel WHERE guildid = " + guild.getIdLong());

        try {
            if (set.next()) {
                channelid = set.getLong("channelid");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return channelid;
    }

    //Getter Setter
    public Guild getGuild() {
        return guild;
    }

    public AudioPlayer getPlayer() {
        return player;
    }

    public Queue getQueue() {
        return queue;
    }

    public MusicEmbeds getEmbeds() {
        return embeds;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean getPaused(){
        return isPaused;
    }



}
