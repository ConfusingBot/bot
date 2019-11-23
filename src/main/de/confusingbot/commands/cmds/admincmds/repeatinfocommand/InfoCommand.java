package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class InfoCommand implements ServerCommand
{

    private SQL sql = new SQL();
    private Embeds embeds = new Embeds();
    int maxInfos = 3;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- repeatinfo add [@channel] ([Time max 10 | default = 2]) [info]     !TimeStamp is always 2h
        //- repeatinfo remove [1, 2, 3]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length == 1)
            {
                listInfoCommand(message, args, channel);
            }
            else if (args.length >= 3)
            {
                switch (args[1])
                {
                    case "add":
                        addCommand(message, args, channel);
                        break;
                    case "remove":
                        removeCommand(message.getGuild(), args, channel);
                        break;
                    default:
                        //Usage
                        embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void listInfoCommand(Message message, String[] args, TextChannel channel)
    {

    }

    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 5)
        {
            TextChannel textChannel = message.getMentionedChannels().get(0);

            if (textChannel != null && args[2].contains(textChannel.getName()))
            {
                int time = 2;
                try
                {
                    time = Integer.parseInt(args[3]);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }

                Guild guild = textChannel.getGuild();
                if (!sql.hasEnabledCountOfInfos(guild.getIdLong(), maxInfos))
                {
                    StringBuilder command = new StringBuilder();
                    for (int i = 3; i < args.length; i++) command.append(args[i] + " ");
                    String infoString = command.toString().trim();

                    //SQL
                    sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), infoString, time);

                    //Message
                    embeds.SuccessfulAddedBumpCommand(channel);
                }
                else
                {
                    //Error
                    embeds.OnlyXAllowedInfoCommandsError(channel, maxInfos);
                }
            }
            else
            {
                //Error
                embeds.HaveNotMentionedATextChannelError(channel);
            }
        }
        else
        {
            //Usage
            embeds.AddUsage(channel);
        }
    }

    private void removeCommand(Guild guild, String[] args, TextChannel channel)
    {
        if (args.length == 3)
        {
            int number = maxInfos + 1;
            try
            {
                number = Integer.parseInt(args[2]);
            } catch (NumberFormatException e)
            {
                e.printStackTrace();
            }

            if (sql.hasEnabledCountOfInfos(guild.getIdLong(), number))
            {
                //SQL
                sql.removeFormSQL(guild.getIdLong(), number);

                //Message
                embeds.SuccessfulRemovedRepeatInfo(channel);
            }
            else
            {
                //Error
                embeds.NoExistingInfoAtIndexError(channel);
            }
        }
        else
        {
            //Usage
            embeds.RemoveUsage(channel);
        }
    }

}
