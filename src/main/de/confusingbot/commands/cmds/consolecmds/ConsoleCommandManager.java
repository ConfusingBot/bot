package main.de.confusingbot.commands.cmds.consolecmds;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommandManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleCommandManager
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
                    switch (line)
                    {
                        case "exit":

                            Main.botOffline = true;
                            if (shardManager != null)
                            {
                                //save online time of bot
                                InfoCommandManager.botInfo.updateBotOnlineTime(Main.botStartTime, true);

                                shardManager.setStatus(OnlineStatus.OFFLINE);
                                shardManager.shutdown();
                                LiteSQL.disconnect();
                                System.out.println("Bot offline!");

                                //Stop Timer
                                stopTimer();

                                reader.close();
                                System.exit(0);
                            }

                            break;

                        case "servers":

                            System.out.println("--Servers------------------------------------------------");
                            Main.INSTANCE.shardManager.getShards().forEach(jda -> {
                                for (Guild guild : jda.getGuilds())
                                {
                                    System.out.println(guild.getName() + " -> " + guild.getIdLong());
                                }
                            });
                            System.out.println("--------------------------------------------------------");

                            break;

                        default:
                            System.out.println("No valid Command!");
                            break;
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
        Main.INSTANCE.generalTimer.stopTimer();
        Main.INSTANCE.statusTimer.stopTimer();
    }


}
