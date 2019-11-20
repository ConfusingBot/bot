package main.de.confusingbot.timer;

import main.de.confusingbot.channels.CheckQuestionChannel;

import java.util.Timer;
import java.util.TimerTask;

public class CheckQuestionTimer
{

    private CheckQuestionChannel checkQuestionChannel;
    private Timer timer;

    public CheckQuestionTimer()
    {
        timer = new Timer();
        this.checkQuestionChannel = new CheckQuestionChannel();
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
                checkQuestionChannel.loopQuestionCommands();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 60);//TODO to hours
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
