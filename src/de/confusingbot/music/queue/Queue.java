package de.confusingbot.music.queue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import de.confusingbot.music.manage.MusicController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Queue
{

    private List<AudioTrack> queueList;
    private MusicController controller;

    public Queue(MusicController controller)
    {
        this.controller = controller;
        this.queueList = new ArrayList<AudioTrack>();
    }

    public boolean hasNext()
    {
        if (this.queueList.size() >= 1)
        {
            AudioTrack track = queueList.remove(0);//the last played song

            if (track != null)
            {
                try
                {
                    this.controller.getPlayer().playTrack(track);
                    return true;
                } catch (Exception e)
                {
                    if (e.getMessage().contains("Cannot play the same instance of a track twice"))
                    {
                        this.controller.getPlayer().playTrack(track.makeClone());
                        return true;
                    }
                }

            }
        }

        return false;
    }

    public void addTrackToQueue(AudioTrack track)
    {
        this.queueList.add(track);

        if (controller.getPlayer().getPlayingTrack() == null)
        {
            hasNext();
        }
    }

    public void Shuffle()
    {
        Collections.shuffle(queueList);
    }

    public void DeleteAtIndex(int index)
    {
        queueList.remove(index);
    }

    //Getter Setter
    public MusicController getController()
    {
        return controller;
    }

    public void setController(MusicController controller)
    {
        this.controller = controller;
    }

    public List<AudioTrack> getQueueList()
    {
        return queueList;
    }

    public void setQueueList(List<AudioTrack> queueList)
    {
        this.queueList = queueList;
    }
}
