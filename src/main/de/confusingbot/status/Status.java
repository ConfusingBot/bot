package main.de.confusingbot.status;


import java.util.List;
import java.util.Random;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Status
{
    private ShardManager shardMan;

    String[] status = new String[]{"ConfusingGames", "on %servers servers", "DiscrodBot", "Nothing"};
    int timeBetweenStatuses = 15;//Seconds

    public Status(ShardManager shardMan)
    {
        this.shardMan = shardMan;
    }

    int currentTime = timeBetweenStatuses;

    public void onSecond()
    {
        if (currentTime <= 0)
        {
            Random rand = new Random();
            int i = rand.nextInt(status.length);

            shardMan.getShards().forEach(jda -> {
                String text = status[i]
                        .replaceAll("%members", "" + jda.getUsers().size())
                        .replaceAll("%servers", "" + jda.getGuilds().size());


                jda.getPresence().setActivity(Activity.playing((text)));
            });
            currentTime = timeBetweenStatuses;
        }
        else
        {
            currentTime--;
        }
    }
}

