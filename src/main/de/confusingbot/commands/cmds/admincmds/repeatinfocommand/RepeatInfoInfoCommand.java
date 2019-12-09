package main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class RepeatInfoInfoCommand implements ServerCommand {


    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        //- repeatinfo add [@channel] ([Time max 10 | default = 2]) [info]     !TimeStamp is always 2h
        //- repeatinfo remove [1, 2, 3]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR)) {
            if (args.length == 1) {
                listInfoCommand(args, channel, message.getGuild());
            } else if (args.length >= 3) {
                switch (args[1]) {
                    case "add":
                        addCommand(message, args, channel);
                        break;
                    case "remove":
                        removeCommand(message.getGuild(), args, channel);
                        break;
                    default:
                        //Usage
                        RepeatInfoCommandManager.embeds.GeneralUsage(channel);
                        break;
                }
            } else {
                //Usage
                RepeatInfoCommandManager.embeds.GeneralUsage(channel);
            }
        } else {
            //Error
            RepeatInfoCommandManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    //ListCommand
    private void listInfoCommand(String[] args, TextChannel channel, Guild guild) {
        if (args.length == 3) {

            List<Integer> repeatInfoIDs = RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong());

            if (repeatInfoIDs.size() > 0) {

                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < repeatInfoIDs.size(); i++) {
                    builder.append(i + "| " + RepeatInfoCommandManager.sql.getRepeatInfoByID(guild.getIdLong(), repeatInfoIDs.get(i)) + "\n\n");
                }

                //Message
                RepeatInfoCommandManager.embeds.ListRepeatInfosEmbed(channel, builder.toString().trim());

            } else {
                //Error
                RepeatInfoCommandManager.embeds.NoExistingRepeatInfos(channel);
            }
        } else {
            //Usage
            RepeatInfoCommandManager.embeds.ListUsage(channel);
        }
    }

    //AddCommand
    private void addCommand(Message message, String[] args, TextChannel channel) {

        if (args.length >= 5) {
            TextChannel textChannel = message.getMentionedChannels().get(0);
            Guild guild = textChannel.getGuild();

            if (textChannel != null && args[2].contains(textChannel.getName())) {
                int time = 0;
                String timeString = args[3];

                if (CommandsUtil.isNumeric(timeString)) {
                    time = Integer.parseInt(timeString);

                    if (RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong()).size() < RepeatInfoCommandManager.maxInfos) {
                        StringBuilder command = new StringBuilder();

                        for (int i = 3; i < args.length; i++) command.append(args[i] + " ");
                        String infoString = command.toString().trim();

                        //SQL
                        RepeatInfoCommandManager.sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), infoString, time);

                        //Message
                        RepeatInfoCommandManager.embeds.SuccessfulAddedBumpCommand(channel);
                    } else {
                        //Error
                        RepeatInfoCommandManager.embeds.OnlyXAllowedInfoCommandsError(channel, RepeatInfoCommandManager.maxInfos);
                    }

                } else {
                    //Error
                    RepeatInfoCommandManager.embeds.NoMentionedTimeError(channel);
                }

            } else {
                //Error
                RepeatInfoCommandManager.embeds.NoMentionedTextChannelError(channel);
            }

        } else {
            //Usage
            RepeatInfoCommandManager.embeds.AddUsage(channel);
        }
    }

    //RemoveCommand
    private void removeCommand(Guild guild, String[] args, TextChannel channel) {
        if (args.length == 3) {
            String numberString = args[2];
            if (CommandsUtil.isNumeric(numberString)) {
                int number = Integer.parseInt(numberString);
                List<Integer> repeatInfoIDs = RepeatInfoCommandManager.sql.getRepeatInfoIDs(guild.getIdLong());

                if (repeatInfoIDs.size() >= number && number > 0) {

                    //SQL
                    RepeatInfoCommandManager.sql.removeFormSQL(guild.getIdLong(), repeatInfoIDs.get(number));

                    //Message
                    RepeatInfoCommandManager.embeds.SuccessfulRemovedRepeatInfo(channel);

                } else {
                    //Error
                    RepeatInfoCommandManager.embeds.NoExistingInfoAtIndexError(channel);
                }
            } else {
                //Error
                RepeatInfoCommandManager.embeds.NoValidNumberError(channel, numberString);
            }
        } else {
            //Usage
            RepeatInfoCommandManager.embeds.RemoveUsage(channel);
        }
    }

}
