package main.de.confusingbot.commands.cmds.consolecmds;

import main.de.confusingbot.Main;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Shutdown
{

    public static void Bot(ShardManager shardManager)
    {
        new Thread(() -> {
            String line = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try
            {
                while ((line = reader.readLine()) != null && !Main.botOffline)
                {
                    if (line.equalsIgnoreCase("exit"))
                    {
                        Main.botOffline = true;
                        if (shardManager != null)
                        {
                            shardManager.setStatus(OnlineStatus.OFFLINE);
                            shardManager.shutdown();
                            LiteSQL.disconnect();
                            System.out.println("Bot offline!");

                            //Stop Timer
                            stopTimer();

                            reader.close();
                            break;

                        }
                    }
                    else
                    {
                        System.out.println("Use 'exit' to shutdown.");
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        }).start();
    }

    private static void stopTimer()
    {
        Main.INSTANCE.checkQuestionTimer.stopTimer();
        Main.INSTANCE.bumpTimer.stopTimer();
        Main.INSTANCE.statusTimer.stopTimer();
    }


}
