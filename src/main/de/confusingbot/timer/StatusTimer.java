package main.de.confusingbot.timer;

import main.de.confusingbot.channels.CheckQuestionChannel;
import main.de.confusingbot.status.Status;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.Timer;
import java.util.TimerTask;

public class StatusTimer
{
    private Status status;
    private Timer timer;

    public StatusTimer(ShardManager shardManager)
    {
        timer = new Timer();
        this.status = new Status(shardManager);
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
                status.onSecond();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 30);//30s
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
