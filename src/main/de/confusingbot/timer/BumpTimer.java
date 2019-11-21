package main.de.confusingbot.timer;

import main.de.confusingbot.channels.BumpChannel;

import java.util.Timer;
import java.util.TimerTask;

public class BumpTimer
{
    private BumpChannel bumpChannel;
    private Timer timer;

    public BumpTimer()
    {
        timer = new Timer();
        bumpChannel = new BumpChannel();
    }

    //Timer
    public void startTimer()
    {
        //Create Timer Loop
        TimerTask timeTask = new TimerTask()
        {
            @Override
            public void run()
            {
                bumpChannel.loopBumpCommand();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 60 * 60 * 2);//2h
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
