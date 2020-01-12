package main.de.confusingbot.timer;

import main.de.confusingbot.commands.cmds.admincmds.eventcommand.UpdateEvents;
import main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand.UpdateRepeatInfo;
import main.de.confusingbot.commands.cmds.admincmds.votecommand.UpdateVotes;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.UpdateInfos;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.UpdateQuestionChannels;

import java.util.Timer;
import java.util.TimerTask;

public class GeneralTimer
{

    private UpdateQuestionChannels updateQuestionChannels;
    private UpdateVotes updateVotes;
    private UpdateRepeatInfo updateRepeatInfo;
    private UpdateEvents updateEvents;
    private UpdateInfos updateInfos;

    private Timer timer;

    public GeneralTimer()
    {
        timer = new Timer();
        this.updateQuestionChannels = new UpdateQuestionChannels();
        this.updateVotes = new UpdateVotes();
        this.updateRepeatInfo = new UpdateRepeatInfo();
        this.updateEvents = new UpdateEvents();
        this.updateInfos = new UpdateInfos();
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
                updateRepeatInfo.onSecond();
                updateEvents.onSecond();
                updateInfos.onSecond();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 60 * 5);
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
