package main.de.confusingbot.commands.cmds.admincmds.autoremovecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.music.manage.MusicController;
import net.dv8tion.jda.api.Permission;

import java.util.concurrent.ConcurrentHashMap;

public class AutoRemoveManager
{
    public static Embeds embeds = new Embeds();
    public static Permission permission = Permission.MESSAGE_MANAGE;

    public static ConcurrentHashMap<Long, AutoRemove> controller = new ConcurrentHashMap<Long, AutoRemove>();

    public static AutoRemove getAutoRemove(long guildid)
    {
        AutoRemove autoRemove = null;

        if (controller.containsKey(guildid))
        {
            autoRemove = controller.get(guildid);
        }
        else
        {
            autoRemove = new AutoRemove(Main.INSTANCE.shardManager.getGuildById(guildid));

            controller.put(guildid, autoRemove);
        }

        return autoRemove;
    }
}
