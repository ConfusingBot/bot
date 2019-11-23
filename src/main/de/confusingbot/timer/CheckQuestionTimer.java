package main.de.confusingbot.timer;

import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.UpdateQuestionChannels;

import java.util.Timer;
import java.util.TimerTask;

public class CheckQuestionTimer
{

    private UpdateQuestionChannels updateQuestionChannels;
    private Timer timer;

    public CheckQuestionTimer()
    {
        timer = new Timer();
        this.updateQuestionChannels = new UpdateQuestionChannels();
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
                updateQuestionChannels.onSecond();
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
