package main.de.confusingbot.commands.cmds.defaultcmds.helpcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.PrivateCommand;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand
{
    public HelpCommand()
    {
        HelpManager.insertHelp();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

       sendEmbed(HelpManager.getHelp(args), member);
    }

    private void sendEmbed(EmbedBuilder builder, Member member)
    {
        member.getUser().openPrivateChannel().queue((privateChannel) -> {
            privateChannel.sendMessage(builder.build()).queue();
        });
    }

}
