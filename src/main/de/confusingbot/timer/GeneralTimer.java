package main.de.confusingbot.timer;

import main.de.confusingbot.commands.cmds.admincmds.votecommand.UpdateVotes;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.UpdateQuestionChannels;

import java.util.Timer;
import java.util.TimerTask;

public class GeneralTimer
{

    private UpdateQuestionChannels updateQuestionChannels;
    private UpdateVotes updateVotes;

    private Timer timer;

    public GeneralTimer()
    {
        timer = new Timer();
        this.updateQuestionChannels = new UpdateQuestionChannels();
        this.updateVotes = new UpdateVotes();
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
                updateVotes.onSecond();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 60);//TODO 5min
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
