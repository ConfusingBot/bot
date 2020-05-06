package main.de.confusingbot.commands.cmds.ownercmds.leavecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.*;

public class LeaveServerCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    main.de.confusingbot.listener.commandlistener.Embeds commandsListenerEmbeds = new main.de.confusingbot.listener.commandlistener.Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());
        boolean isAdmin = member.getUser().getId().toString() == "637931838052237312" || member.getUser().getId().toString() == "333341131053989890";

        if (isAdmin)
        {
            if (args.length == 2)
            {
                if (args[1].equals("this"))
                {
                    LeaveServerByID(channel.getGuild().getId(), channel);
                }
                else
                {
                    String idString = args[1];
                    LeaveServerByID(idString, channel);
                }
            }
        }
        else
        {
            //UnknownCommand Information
            commandsListenerEmbeds.CommandDoesNotExistsInformation(channel);
        }
    }

    private void LeaveServerByID(String idString, TextChannel channel)
    {
        if (CommandsUtil.isNumeric(idString))
        {
            long id = Long.parseLong(idString);

            Guild guild = Main.INSTANCE.shardManager.getGuildById(id);

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
