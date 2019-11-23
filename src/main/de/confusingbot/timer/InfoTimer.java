package main.de.confusingbot.timer;

import main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand.InfoHandler;

import java.util.Timer;
import java.util.TimerTask;

public class InfoTimer
{
    private InfoHandler infoHandler;
    private Timer timer;

    public InfoTimer()
    {
        timer = new Timer();
        infoHandler = new InfoHandler();
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
                infoHandler.loopInfos();
            }
        };

        timer.schedule(timeTask, 0l, 1000 * 60 * 60 * 1);//1h
    }

    public void stopTimer()
    {
        timer.cancel();
        timer.purge();
    }
}
