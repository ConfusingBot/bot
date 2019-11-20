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

public class Status {

    private Thread loop;
    private ShardManager shardMan;

    String[] status = new String[] {"ConfusingGames", "on %servers servers", "DiscrodBot", "Nothing"};
    int timeBetweenStatuses = 15;//Seconds

    public Status(ShardManager shardMan) {
        this.shardMan = shardMan;
    }

    public void runLoop() {
        this.loop = new Thread(() -> {

            long time = System.currentTimeMillis();

            while(!Main.botOffline) {
                if(System.currentTimeMillis() >= time + 1000) {
                    time = System.currentTimeMillis();
                    onSecond();
                }
            }
        });

        this.loop.setName("Loop");
        this.loop.start();
    }

    int currentTime = timeBetweenStatuses;
    public void onSecond() {
        if(currentTime <= 0) {
            Random rand = new Random();
            int i = rand.nextInt(status.length);

            shardMan.getShards().forEach(jda -> {
                String text = status[i]
                        .replaceAll("%membersonline", "" + getOnlineMember())
                        .replaceAll("%members", "" + jda.getUsers().size())
                        .replaceAll("%servers", "" + jda.getGuilds().size());



                jda.getPresence().setActivity(Activity.playing((text)));
            });
            currentTime = timeBetweenStatuses;
        }else {
            currentTime--;
        }
    }

    public void stopLoop() {
        if(loop != null) {
            loop.interrupt();
        }
    }

    private int getOnlineMember(){
        TextChannel textChannel = shardMan.getTextChannelById(637567992791695371l);//entrence hall
        Guild guild = textChannel.getGuild();
        List<Member> members = guild.getMembers();

        int online = 0;

        for(Member memb : members) {
            if(memb.getOnlineStatus() != OnlineStatus.OFFLINE) {
                if(!memb.getUser().isBot()) {
                    online++;
                }
            }
        }
        return online;
    }

}

