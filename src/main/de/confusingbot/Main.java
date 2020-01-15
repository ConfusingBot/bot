package main.de.confusingbot;


import main.de.confusingbot.listener.botlistener.BotListener;
import main.de.confusingbot.listener.commandlistener.CommandListener;
import main.de.confusingbot.listener.joinlistener.JoinListener;
import main.de.confusingbot.listener.reactionlistener.ReactionListener;
import main.de.confusingbot.listener.voicelistener.VoiceListener;
import main.de.confusingbot.manage.commands.CommandManager;
import main.de.confusingbot.commands.cmds.consolecmds.ConsoleCommandManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.timer.GeneralTimer;
import main.de.confusingbot.timer.StatusTimer;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Main
{

    public static Main INSTANCE;

    public ShardManager shardManager;
    private CommandManager cmdManager;

    //ConfusingTestBot Token: NjQ3MTQ3NDkyOTQwNTc4ODI2.Xe0BKw.le3xRsP2stezvJbhXnr8gIKtaSQ
    //ConfusingBot Token: NjM4NzYwNDYwODEyMDI1ODY2.XhjmyQ.jjGdRKCy8prSzczjp-8UfvPtTgM
    private String token = "NjQ3MTQ3NDkyOTQwNTc4ODI2.Xe0BKw.le3xRsP2stezvJbhXnr8gIKtaSQ";
    public static String version = "0.0.07";
    public static long linesOfCode = 12432;
    public static String prefix = "- ";
    public static LocalDateTime botStartTime = LocalDateTime.now();

    public static boolean botOffline = false;

    public static void main(String[] args)
    {
        try
        {
            new Main();
        } catch (LoginException e)
        {
            e.printStackTrace();
        }
    }

    public Main() throws LoginException
    {
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

        //Start the Timers
        StartTimer();

        //Commands which you can type in the console
        ConsoleCommands();
    }

    private void Listener(DefaultShardManagerBuilder builder)
    {
        builder.addEventListeners(new CommandListener());
        builder.addEventListeners(new VoiceListener());
        builder.addEventListeners(new ReactionListener());
        builder.addEventListeners(new JoinListener());
        builder.addEventListeners(new BotListener());

    }

    private void ConsoleCommands()
    {
        ConsoleCommandManager.Bot(shardManager);
    }

    public GeneralTimer generalTimer;
    public StatusTimer statusTimer;

    private void StartTimer()
    {
        //Here you have to sleep 5s because otherwise the ShardManger hasn't loaded correctly
        try
        {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        generalTimer = new GeneralTimer();
        generalTimer.startTimer();

        statusTimer = new StatusTimer(shardManager);
        statusTimer.startTimer();

    }

//Getter

    public CommandManager getCmdManager()
    {
        return cmdManager;
    }
}
