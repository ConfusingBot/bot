package main.de.confusingbot.commands.cmds.musiccmds.trackinfocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.musiccmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public Embeds()
    {
        HelpManager.music.add("```yaml\n" + Main.prefix + "trackinfo\n``` ```Gives you some informations about the current playing track```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void TrackInfoUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "trackinfo`", channel, EmbedsUtil.showUsageTime);
    }

    public void NoSongIsPlaying(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("Nothing is playing\uD83D\uDD07", channel, EmbedsUtil.showInfoTime);
    }

    public void TrackInfoEmbed(TextChannel channel, String author, String title, String url, long hours, long minutes, long seconds, long maxHours, long maxMinutes, long maxSeconds, boolean isStream)
    {
        //Build Embed
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#d400ff"));
        builder.setAuthor(author);
        builder.setTitle(title, url);

        String time = ((hours > 0) ? hours + "h " : "") + minutes + "min " + seconds + "s | " + ((maxHours > 0) ? maxHours + "h " : "") + maxMinutes + "min " + maxSeconds + "s";

        builder.setDescription(isStream ? "\uD83D\uDD34 STREAM" : "⏳ " + time);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
