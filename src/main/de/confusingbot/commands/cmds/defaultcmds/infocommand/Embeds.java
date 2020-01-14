package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.time.OffsetDateTime;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "info [@User/bot/server]\n``` ```Give you some informations about the @User/bot/server```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void InfoUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "info [@User/bot]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void CouldNotFindMentionedMember(TextChannel channel, String member)
    {
        EmbedManager.SendErrorEmbed("Could not find member " + member, channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendInfoUserEmbed(TextChannel channel, Member requester, Member member, String joinDate, String createDate, String activityInfo, String roleString)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setFooter("Requested by " + requester.getUser().getName());

        builder.setColor(member.getColor());
        builder.setTimestamp(OffsetDateTime.now());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());

        builder.addField("**Member\uD83D\uDC68\u200D\uD83D\uDCBB:** ", member.getAsMention(), false);
        builder.addField("**Name\uD83D\uDC40:** ", member.getUser().getName(), false);
        builder.addField("**JoinDate\uD83C\uDF08:** ", joinDate, false);
        builder.addField("**CreationDate\uD83D\uDCA5:** ", createDate, false);
        builder.addField("**Activity\uD83D\uDEB4:** ", activityInfo, false);
        builder.addField("**Status\uD83D\uDD54:** ", member.getOnlineStatus().toString(), false);
        builder.addField("**Roles\uD83C\uDF20:** ", roleString, false);

        EmbedManager.SendEmbed(builder, channel, 30);
    }

    public void SendInfoBotEmbed(TextChannel channel, Member requester, String onlineTime, String lastUpdate, String version, long linesOfCode, String creator, String thumbnailURL, String totalServer, String totalUser, String totalChannels)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail(thumbnailURL);
        builder.setAuthor("\uD83D\uDCA1ConfusingBot Info");
        builder.setFooter("Requested by " + requester.getUser().getName());

        builder.setColor(Color.decode("#d4af37"));
        builder.setTimestamp(OffsetDateTime.now());

        builder.addField("**LastUpdate:** ", lastUpdate, true);
        builder.addField("**Version:** ", version, true);
        builder.addField("**OnlineTime:** ", onlineTime, false);
        builder.addField("**Total Servers:** ", totalServer, true);
        builder.addField("**Total Users:** ", totalUser, true);
        builder.addField("**Total Channels:** ", totalChannels, true);
        builder.addField("**Total Lines of Code:** ", Long.toString(linesOfCode), false);
        builder.addField("**Creator:** ", creator, false);
        builder.addField("**Help:** ", "https://discord.gg/xc82F8M", true);

        //TODO load image also in a jar file.. doesn't work yet
        InputStream file = this.getClass().getResourceAsStream("/images/ConfusingGangBanner.png");

        if (file != null)
        {
            builder.setImage("attachment://ConfusingGangBanner.png");
            channel.sendFile(file, "ConfusingGangBanner.png").embed(builder.build()).queue();
        }
        else
        {
            EmbedManager.SendEmbed(builder, channel, 30);
        }


        //EmbedManager.SendEmbed(builder, channel, 30);
    }

    public void SendInfoServerEmbed(TextChannel channel, Member requester, File file, long dates, long textChannels, long voiceChannels, long members, long bots, long emotes, long roles, long categories, String creationTime, Member owner)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail(channel.getGuild().getIconUrl());
        builder.setAuthor("\uD83D\uDCA1Server Info");
        builder.setFooter("Requested by " + requester.getUser().getName());

        builder.setColor(Color.decode("#d4af37"));
        builder.setTimestamp(OffsetDateTime.now());

        builder.addField("**Members:** ", Long.toString(members), true);
        builder.addField("**Bots:** ", Long.toString(bots), true);
        builder.addField("**Roles:** ", Long.toString(roles), true);
        builder.addField("**TextChannels:** ", Long.toString(textChannels), true);
        builder.addField("**VoiceChannels:** ", Long.toString(voiceChannels), true);
        builder.addField("**Categories:**", Long.toString(categories), true);
        builder.addField("**Emotes:**", Long.toString(emotes), true);
        builder.addField("**Creation Time:**", creationTime, true);
        builder.addField("**Owner:** ", owner.getAsMention(), true);

        int minDates = 3;
        if (dates < minDates)
            builder.addField("**Member Graph will be available in " + (minDates - dates) + " day!**", "", false);
        else
        {
            builder.addField("**Member Graph:**", "", false);
            builder.setImage("attachment://memberStats.png");
        }

        //Send embed
        if (file.exists() && dates >= minDates)
        {
            //Send Message with File
            channel.sendFile(file, "memberStats.png").embed(builder.build()).queue(message -> {

                //Delete File
                if(!file.delete()){
                    System.out.println("Failed to delete " + file.getName());
                }
            });
        }
        else
        {
            //Send Message without File
           EmbedManager.SendEmbed(builder, channel, 10);
        }

    }
}
