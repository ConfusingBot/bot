package main.de.confusingbot.commands.cmds.defaultcmds.jokecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class JokeCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    JokeManager manager = new JokeManager();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    public JokeCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- joke [category]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length == 1)
            {
                //- joke
                manager.perform("", channel);
            }
            else if (args.length == 2)
            {
                //- joke mother
                if (!manager.perform(args[1], channel))
                {
                    //Usage
                    embeds.JokeUsage(channel);
                }
            }
            else
            {
                //Usage
                embeds.JokeUsage(channel);
            }
        }
    }
}
