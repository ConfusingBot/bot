package main.de.confusingbot.music.manage;

import main.de.confusingbot.Main;
import main.de.confusingbot.music.manage.MusicController;

import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    public ConcurrentHashMap<Long, MusicController> controller;

    public PlayerManager(){
        this.controller = new ConcurrentHashMap<Long, MusicController>();
    }

    public MusicController getController(long guildid){
        MusicController musicController = null;

        if(this.controller.containsKey(guildid)){
            musicController = this.controller.get(guildid);
        }else{
            musicController = new MusicController(Main.INSTANCE.shardManager.getGuildById(guildid));

            this.controller.put(guildid, musicController);
        }

        return musicController;
    }

    public long getGuildByPlayerHash(int hash){
        for(MusicController controller : this.controller.values()){
            if(controller.getPlayer().hashCode() == hash){
                return controller.getGuild().getIdLong();
            }
        }

        return -1;
    }

}
