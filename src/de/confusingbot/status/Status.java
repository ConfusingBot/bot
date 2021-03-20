package de.confusingbot.status;

import java.util.Random;

import de.confusingbot.Main;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Status
{
    private ShardManager shardMan;

    String[] status = new String[]{Main.prefix + "help | %servers servers"};

    public Status(ShardManager shardMan)
    {
        this.shardMan = shardMan;
    }

    public void onSecond()
    {
        Random rand = new Random();
        int i = rand.nextInt(status.length);

        shardMan.getShards().forEach(jda -> {
            String text = status[i]
                    .replaceAll("%members", "" + jda.getUsers().size())
                    .replaceAll("%servers", "" + jda.getGuilds().size());

            jda.getPresence().setActivity(Activity.streaming((text), "https://www.twitch.tv/bennodev19"));

            //top.gg
            Main.topGGApi.setStats(jda.getGuilds().size());
        });
    }
}

