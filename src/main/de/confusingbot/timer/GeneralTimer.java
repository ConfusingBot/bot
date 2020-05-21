package main.de.confusingbot.timer;

import main.de.confusingbot.commands.cmds.admincmds.eventcommand.UpdateEvents;
import main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand.UpdateRepeatInfo;
import main.de.confusingbot.commands.cmds.admincmds.votecommand.UpdateVotes;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.UpdateInfos;
import main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand.UpdateInvites;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.UpdateQuestionChannels;
import main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand.UpdateYouTubeAnnouncements;

import java.util.Timer;
import java.util.TimerTask;

public class GeneralTimer
{

    private UpdateQuestionChannels updateQuestionChannels;
    private UpdateVotes updateVotes;
    private UpdateRepeatInfo updateRepeatInfo;
    private UpdateEvents updateEvents;
    private UpdateInfos updateInfos;
    private UpdateInvites updateInvites;
    private UpdateYouTubeAnnouncements updateYouTubeAnnouncements;


    private Timer shortTimer;
    private Timer longTimer;

    public GeneralTimer()
    {
        shortTimer = new Timer();
        longTimer = new Timer();
        this.updateQuestionChannels = new UpdateQuestionChannels();
        this.updateVotes = new UpdateVotes();
        this.updateRepeatInfo = new UpdateRepeatInfo();
        this.updateEvents = new UpdateEvents();
        this.updateInfos = new UpdateInfos();
        this.updateInvites = new UpdateInvites();
        this.updateYouTubeAnnouncements = new UpdateYouTubeAnnouncements();
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
                updateInvites.onSecond();
                updateYouTubeAnnouncements.onSecond();
            }
        };
        shortTimer.schedule(timeTask, 0l, 1000 * 60 * 5);// every 5min

/*
        //Create Long Timer Loop
        TimerTask longTimeTask = new TimerTask()
        {
            @Override
            public void run()
            {
                updateInfos.onSecond();
            }
        };
        longTimer.schedule(longTimeTask, 0l,  1000 * 60 * 60 * 6);// every 6h
        */
    }

    public void stopTimer()
    {
        shortTimer.cancel();
        shortTimer.purge();
        longTimer.cancel();
        longTimer.purge();
    }
}
