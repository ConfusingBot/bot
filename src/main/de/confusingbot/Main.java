package main.de.confusingbot;

import main.de.confusingbot.listener.botlistener.BotListener;
import main.de.confusingbot.listener.commandlistener.CommandListener;
import main.de.confusingbot.listener.joinlistener.JoinListener;
import main.de.confusingbot.listener.reactionlistener.ReactionListener;
import main.de.confusingbot.listener.voicelistener.VoiceListener;
import main.de.confusingbot.manage.commands.CommandManager;
import main.de.confusingbot.commands.cmds.consolecmds.ConsoleCommandManager;
import main.de.confusingbot.manage.person.PersonManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import main.de.confusingbot.manage.sql.SQLManager;
import main.de.confusingbot.music.manage.Music;
import main.de.confusingbot.timer.GeneralTimer;
import main.de.confusingbot.timer.StatusTimer;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.discordbots.api.client.DiscordBotListAPI;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class Main
{

    public static Main INSTANCE;

    public ShardManager shardManager;
    private CommandManager cmdManager;

    public static String token = System.getenv("TOKEN");
    public static String topGGToken = System.getenv("TOP_GG_TOKEN");
    public static String version = "0.0.09";
    public static long linesOfCode = 16587;
    public static String prefix = "c/";
    public static LocalDateTime botStartTime = LocalDateTime.now();
    public static boolean botOffline = false;

    //Top.gg
    public static DiscordBotListAPI topGGApi;

    public static void main(String[] args)
    {
        try
        {
            INSTANCE = new Main();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Main() throws LoginException
    {
        LiteSQL.connect();
        SQLManager.onCreate();

        DefaultShardManagerBuilder defaultShardManagerBuilder = DefaultShardManagerBuilder.createDefault(token);

        Music music = new Music();
        PersonManager.instantiatePersons();

        cmdManager = new CommandManager();

        //Listener
        Listener(defaultShardManagerBuilder);

        shardManager = defaultShardManagerBuilder.build();
        System.out.println("Bot online!");

        //Top.gg
        topGGApi = new DiscordBotListAPI.Builder()
                .token(topGGToken)
                .botId("638760460812025866")
                .build();

        //Music
        music.instantiateMusic();

        //Start the Timers
        // StartTimer();

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

        statusTimer = new StatusTimer(shardManager);
        statusTimer.startTimer();

        try
        {
            TimeUnit.SECONDS.sleep(30);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        generalTimer = new GeneralTimer();
        generalTimer.startTimer();
    }

    //Getter
    public CommandManager getCmdManager()
    {
        return cmdManager;
    }
}
