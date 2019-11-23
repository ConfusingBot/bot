package main.de.confusingbot.commands.cmds.defaultcmds.rulescommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class RulesCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- rules
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length == 1)
        {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle("\uD83D\uDDC2**INFORMATION**");
            builder.setColor(Color.decode("#fff400"));
            builder.setDescription("**Purpose**\n" +
                    "```\n" +
                    "This is supposed to be a friendly\uD83D\uDC4D community hub for developers\uD83D\uDC68\uD83C\uDFFD\u200D\uD83D\uDCBB, designer\uD83C\uDF0C and gamers\uD83C\uDFB2 as well as for everybody\uD83D\uDD13 who want to be here\uD83D\uDD3D.\n" +
                    "```\n" +
                    "**Rules**\n" +
                    "```\n" +
                    "\uD83D\uDCCCNo Spam❗️\n" +
                    "\uD83D\uDCCCDon't post illegal, racist, sexist, homophobic or transphobic content\n" +
                    "\uD83D\uDCCCKeep it kind of PG-13, because we might have some younger people here \n" +
                    "  ▶️Show some responsibility for that\n" +
                    "\uD83D\uDCCCPlease be nice, respectful and familiar to everybody.\n" +
                    "\uD83D\uDCCCNo Memes   \uD83D\uDCE2only in stupid-gifts\n" +
                    "\uD83D\uDCCCFind the right channel for your topic \n" +
                    "  ➡️Don't discuss gaming in the programming channel▪️ ▫️ ◾️ \n" +
                    "\uD83D\uDCCCDon't try to sell anything\uD83D\uDCB2\n" +
                    "```\n" +
                    "**Features**\n" +
                    "```\n" +
                    "✔️You can use our ConfusingBot\uD83E\uDD16 ▶️ - help  \n" +
                    "✔️You will get help\uD83D\uDCAF, feedback\uD83C\uDF10 and nearly every thing you want⚜️\n" +
                    "✔️Be in contact with real\uD83D\uDCAF Programmer\uD83D\uDC68\uD83C\uDFFD\u200D\uD83D\uDCBB, Designer\uD83C\uDF04 and Gamer\uD83C\uDCCF \n" +
                    "✔️The server is completely\uD83D\uDD50 free\uD83D\uDD13\n" +
                    "✔️The server is always\uD83D\uDD50 online\uD83D\uDD0B\n" +
                    "```\n" +
                    "**Encourage**\n" +
                    "```\n" +
                    "⚜️Say \"Hello\"\uD83D\uDD90 to your friends\uD83D\uDE04\n" +
                    "⚜️Don't be shy and share\uD83D\uDCC8 your stuff    ⚠️feedback is important⚠️\n" +
                    "⚜️Ask questions❓\n" +
                    "⚜️Report bugs⚔️\n" +
                    "⚜️Make suggestions✅\n" +
                    "```");
            EmbedManager.SendEmbed(builder, channel, 60);
        }
        else
        {
            //Usage
            embeds.RulesCommandUsage(channel);
        }

    }
}
