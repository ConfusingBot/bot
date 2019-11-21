package main.de.confusingbot;


import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.listener.botlistener.BotListener;
import main.de.confusingbot.listener.commandlistener.CommandListener;
import main.de.confusingbot.listener.joinlistener.JoinListener;
import main.de.confusingbot.listener.reactionlistener.ReactionListener;
import main.de.confusingbot.listener.voicelistener.VoiceListener;
import main.de.confusingbot.manage.commands.CommandManager;
import main.de.confusingbot.commands.cmds.consolecmds.Shutdown;
import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;
import main.de.confusingbot.music.Music;
import main.de.confusingbot.status.Status;
import main.de.confusingbot.timer.BumpTimer;
import main.de.confusingbot.timer.CheckQuestionTimer;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static Main INSTANCE;

    public ShardManager shardManager;
    private CommandManager cmdManager;

    private String token = "NjM4NzYwNDYwODEyMDI1ODY2.Xbp5ow.YvLOJN7rsL_4kJ90tQdluYa5TR4";
    public static String prefix = "- ";

    public static boolean botOffline = false;

    public static void main(String[] args) {

        try {
            new Main();
        } catch (LoginException e) {
            e.printStackTrace();
        }

    }

    public Main() throws LoginException {
        INSTANCE = this;

        LiteSQL.connect();
        SQLManager.onCreate();

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(token);

        Music music = new Music();

        cmdManager = new CommandManager();

        //Listener
        Listener(builder);

        shardManager = builder.build();
        System.out.println("Bot online!");

        music.instantiateMusic();

        //Bot Methods to start at te beginning
        StartBotFunctions();

        //Start the Timers
        StartTimer();

        //Commands which you can type in the console
        ConsoleCommands();
    }

    private void Listener(DefaultShardManagerBuilder builder) {
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new VoiceListener());
        builder.addEventListeners(new ReactionListener());
        builder.addEventListeners(new JoinListener());
        builder.addEventListeners(new BotListener());

    }

    private void ConsoleCommands() {
        Shutdown.discrodBot(shardManager);
    }

    public Status status;

    private void StartBotFunctions() {
        status = new Status(shardManager);
        status.runLoop();

        HelpManager.insertHelp();
    }

    public CheckQuestionTimer checkQuestionTimer;
    public BumpTimer bumpTimer;
    private void StartTimer(){
        //Here you have to sleep 5s because otherwise the ShardManger hasn't loaded correctly
        try
        {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        checkQuestionTimer = new CheckQuestionTimer();
        checkQuestionTimer.startTimer();

        bumpTimer = new BumpTimer();
        bumpTimer.startTimer();
    }
//Getter

    public CommandManager getCmdManager() {
        return cmdManager;
    }
}
