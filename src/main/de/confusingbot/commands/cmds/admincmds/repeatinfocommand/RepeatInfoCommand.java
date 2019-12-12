package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class RepeatInfoCommand implements ServerCommand
{

    public RepeatInfoCommand()
    {
        RepeatInfoCommandManager.embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- repeatinfo add [@channel] [time] ([color]) ([title])  [info]
        //- repeatinfo remove [1, 2, 3]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "add":
                        addCommand(message, args, channel);
                        break;
                    case "remove":
                        removeCommand(message.getGuild(), args, channel);
                        break;
                    case "list":
                        listInfoCommand(args, channel, message.getGuild());
                        break;
                    default:
                        //Usage
                        RepeatInfoCommandManager.embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                RepeatInfoCommandManager.embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            RepeatInfoCommandManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    //ListCommand
    private void listInfoCommand(String[] args, TextChannel channel, Guild guild)
    {
        if (args.length == 2)
        {

            List<Integer> repeatInfoIDs = RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong());

            if (repeatInfoIDs.size() > 0)
            {

                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < repeatInfoIDs.size(); i++)
                {
                    builder.append((i + 1) + "| " + RepeatInfoCommandManager.sql.getRepeatInfoByID(guild.getIdLong(), repeatInfoIDs.get(i)) + "\n\n");
                }

                //Message
                RepeatInfoCommandManager.embeds.ListRepeatInfosEmbed(channel, builder.toString().trim());

            }
            else
            {
                //Error
                RepeatInfoCommandManager.embeds.NoExistingRepeatInfosInformation(channel);
            }
        }
        else
        {
            //Usage
            RepeatInfoCommandManager.embeds.ListUsage(channel);
        }
    }

    //AddCommand
    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 5)
        {
            TextChannel textChannel = message.getMentionedChannels().get(0);
            Guild guild = textChannel.getGuild();

            if (textChannel != null && args[2].contains(textChannel.getName()))
            {
                //SetTime
                int time = 0;
                String timeString = args[3];
                if (CommandsUtil.isNumeric(timeString))
                {
                    time = Integer.parseInt(timeString);
                    int textStartIndex = 4;

                    //SetColor
                    String hexColor = "#fff";
                    if (args[4].startsWith("#"))
                    {
                        if (CommandsUtil.isColor(args[4]))
                        {
                            hexColor = args[4];
                            textStartIndex = 5;
                        }
                    }

                    if (RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong()).size() < RepeatInfoCommandManager.maxRepeatInfos)
                    {
                        //SetTitle And Info
                        StringBuilder command = new StringBuilder();
                        for (int i = textStartIndex; i < args.length; i++)
                        {
                            command.append(args[i] + " ");
                        }
                        String wholeString = command.toString().trim();
                        String info = " ";
                        String title = " ";
                        if (wholeString.contains(RepeatInfoCommandManager.infoKey))
                        {
                            String[] titleAndInfoString = wholeString.split(RepeatInfoCommandManager.infoKey);
                            title = titleAndInfoString[0];
                            info = titleAndInfoString[1];
                        }
                        else
                        {
                            info = wholeString;
                        }

                        //SQL
                        RepeatInfoCommandManager.sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), time, hexColor, title, info);

                        //Message
                        RepeatInfoCommandManager.embeds.SuccessfulAddedRepeatInfo(channel);
                    }
                    else
                    {
                        //Error
                        RepeatInfoCommandManager.embeds.OnlyXAllowedInfoCommandsError(channel, RepeatInfoCommandManager.maxRepeatInfos);
                    }
                }
                else
                {
                    //Error
                    RepeatInfoCommandManager.embeds.NoMentionedTimeError(channel);
                }
            }
            else
            {
                //Error
                RepeatInfoCommandManager.embeds.NoMentionedTextChannelError(channel);
            }
        }
        else
        {
            //Usage
            RepeatInfoCommandManager.embeds.AddUsage(channel);
        }
    }

    //RemoveCommand
    private void removeCommand(Guild guild, String[] args, TextChannel channel)
    {
        if (args.length == 3)
        {
            String numberString = args[2];
            if (CommandsUtil.isNumeric(numberString))
            {
                int number = Integer.parseInt(numberString);
                List<Integer> repeatInfoIDs = RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong());

                if (repeatInfoIDs.size() >= number && number > 0)
                {

                    //SQL
                    RepeatInfoCommandManager.sql.removeFormSQL(guild.getIdLong(), repeatInfoIDs.get(number - 1));

                    //Message
                    RepeatInfoCommandManager.embeds.SuccessfulRemovedRepeatInfo(channel, number);

                }
                else
                {
                    //Error
                    RepeatInfoCommandManager.embeds.NoExistingInfoAtIndexError(channel);
                }
            }
            else
            {
                //Error
                RepeatInfoCommandManager.embeds.NoValidNumberError(channel, numberString);
            }
        }
        else
        {
            //Usage
            RepeatInfoCommandManager.embeds.RemoveUsage(channel);
        }
    }

}
