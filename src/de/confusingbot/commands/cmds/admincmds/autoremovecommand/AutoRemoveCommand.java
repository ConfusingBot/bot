package de.confusingbot.commands.cmds.admincmds.autoremovecommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;


public class AutoRemoveCommand implements ServerCommand
{
    private Embeds embeds = AutoRemoveManager.embeds;

    public AutoRemoveCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        String[] args = CommandsUtil.messageToArgs(message);

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(AutoRemoveManager.permission))
            {
                if (args.length == 1)
                {
                    AutoRemove autoRemove = AutoRemoveManager.getAutoRemove(channel.getGuild().getIdLong());
                    autoRemove.setCanAutoRemove(!autoRemove.getCanAutoRemove());

                    //Message
                    embeds.AutoRemoveSetStatus(channel, autoRemove.getCanAutoRemove());
                }
                else
                {
                    //Usage
                    embeds.AutoRemoveUsage(channel);
                }
            }
            else
            {
                //Error
                embeds.NoPermissionError(channel, AutoRemoveManager.permission);
            }
        }
    }
}
