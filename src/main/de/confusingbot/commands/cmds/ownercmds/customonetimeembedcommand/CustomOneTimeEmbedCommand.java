package main.de.confusingbot.commands.cmds.ownercmds.customonetimeembedcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class CustomOneTimeEmbedCommand implements ServerCommand
{

    private Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(channel, Permission.ADMINISTRATOR))
            {

                String[] args = CommandsUtil.messageToArgs(message);
                EmbedManager.DeleteMessageByID(channel, message.getIdLong());

                if (args.length == 1)
                {
                    //CustomEmbedCommand
                    limitedVoiceChannelDescription(channel);
                }
                else
                {
                    //Usage
                    embeds.Usage(channel);
                }
            }
            else
            {
                //Error
                embeds.NoPermissionError(channel, Permission.ADMINISTRATOR);
            }
        }
    }

    private void servers(TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("\uD83C\uDF04**DiscrodServer**");
        builder.setColor(Color.decode("#a103fc"));
        builder.setDescription("\uD83D\uDD3D Here you can find other **awesome** DiscordServer with **awesome** people\n So feel free and discover the **wide** DiscordServer world\uD83D\uDDFA");

        EmbedManager.SendEmbed(builder, channel, 0);

        channel.sendMessage("https://discord.gg/Ye6zzfQ").queue();
        channel.sendMessage("https://discord.gg/vrErfxa").queue();
    }

    private void roles(TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("\uD83D\uDCA1**USAGE**");
        builder.setColor(Color.decode("#fff400"));
        builder.setDescription("▶️**Choose your roles by reacting with the appropriate icons**◀️ \n\n" +
                "⚠️**Everybody** is able to **@** these roles in messages so choose wisely what you'd like to discuss on this server \uD83D\uDE03");

        EmbedManager.SendEmbed(builder, channel, 0);
    }

    private void limitedVoiceChannelDescription(TextChannel channel)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("\uD83D\uDCA1**USAGE**");
        builder.setColor(Color.decode("#fff400"));
        builder.setDescription("**Create your own Limited-Voice-Channel\uD83D\uDCE2 by joining a VoiceChannel**\n\n" +
                "Join a **Channel** with the desired **user limit** below this **TextChannel\uD83D\uDCC4** \n" +
                "and it will **automatically** create your **own Voice-Channel**\uD83C\uDFA4 \n\n" +
                "▶️ Have fun with your friends\uD83D\uDE04");

        EmbedManager.SendEmbed(builder, channel, 0);
    }


}
