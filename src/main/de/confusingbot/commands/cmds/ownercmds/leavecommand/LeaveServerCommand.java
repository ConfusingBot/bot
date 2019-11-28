package main.de.confusingbot.commands.cmds.ownercmds.leavecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.listener.botlistener.SQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;

public class LeaveServerCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    ShardManager shardManager = Main.INSTANCE.shardManager;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.getUser().getName().equals("ConfusingFutureGames"))
        {
            if (args.length == 1)
            {
                LeaveServerByID(channel.getGuild().getId(), channel);
            }
            else if (args.length == 2)
            {
                LeaveServerByID(args[1], channel);
            }
        }
    }

    private void LeaveServerByID(String idString, TextChannel channel)
    {
        if (CommandsUtil.isNumeric(idString))
        {
            long id = Long.parseLong(idString);
            Guild guild = channel.getGuild(); //TODO change this

                if (guild != null)
                {
                    guild.leave().queue();
                    System.out.println("You successfully removed the bot form: " + guild.getName());
                }
                else
                {
                    embeds.CouldNotFindGuildByIDError(channel, idString);
                }
        }
        else
        {
            embeds.NoValidIdError(channel, idString);
        }
    }
}
