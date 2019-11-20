package main.de.confusingbot.commands.cmds.admincmds.bumpbotcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

public class BumpBotCommand implements ServerCommand
{

    private SQL sql = new SQL();
    private Embeds embeds = new Embeds();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- bump add [@channel] [bumpCommand]    !TimeStamp is always 2h

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length > 2)
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
    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 4)
        {
            TextChannel textChannel = message.getMentionedChannels().get(0);

            if (textChannel != null)
            {
                Guild guild = textChannel.getGuild();
                if (!sql.ExistsInSQL(guild.getIdLong()))
                {
                    StringBuilder command = new StringBuilder();
                    for (int i = 3; i < args.length; i++) command.append(args[i] + " ");
                    String commandString = command.toString().trim();

                    //SQL
                    sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), commandString);

                    //Message
                    embeds.SuccessfulAddedBumpCommand(channel);
                }
                else
                {
                    //Error
                    embeds.OnlyOneAllowedBumpCommandAllowedError(channel);
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
        if (args.length == 2)
        {
            if (sql.ExistsInSQL(guild.getIdLong()))
            {
                //SQL
                sql.removeFormSQL(guild.getIdLong());

                //Message
                embeds.SuccessfulRemovedBumpCommand(channel);
            }
            else
            {
                //Error
                embeds.NoExistingBumpCommandError(channel);
            }
        }
        else
        {
            //Usage
            embeds.RemoveUsage(channel);
        }
    }

}
