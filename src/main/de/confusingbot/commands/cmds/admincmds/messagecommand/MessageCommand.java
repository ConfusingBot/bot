package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class MessageCommand implements ServerCommand
{

    private String messageKey = "MESSAGE:";

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //- message add welcome [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: Welcome @newMember to the server look at #rule
        //- message remove welcome

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(Permission.ADMINISTRATOR))
        {
            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "add":
                        if (args.length >= 3)
                        {
                            switch (args[2])
                            {
                                case "welcome":
                                    WelcomeMessageAddCommand(channel, args, message);
                                    break;
                                case "leave":
                                    //TODO
                                    break;
                                default:
                                    //Usage
                                    MessageManager.embeds.GeneralUsage(channel);
                                    break;
                            }
                        }
                        else
                        {
                            //Usage
                            MessageManager.embeds.GeneralUsage(channel);
                        }
                        break;
                    case "remove":
                        if (args.length >= 3)
                        {
                            switch (args[2])
                            {
                                case "welcome":
                                    WelcomeMessageRemoveCommand(channel, args);
                                    break;
                                case "leave":
                                    //TODO
                                    break;
                                default:
                                    //Usage
                                    MessageManager.embeds.GeneralUsage(channel);
                                    break;
                            }
                        }
                        else
                        {
                            //Usage
                            MessageManager.embeds.GeneralUsage(channel);
                        }
                        break;
                    default:
                        //Usage
                        MessageManager.embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                MessageManager.embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            MessageManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void WelcomeMessageAddCommand(TextChannel channel, String[] args, Message message)
    {
        Guild guild = channel.getGuild();
        if (args.length > 3)
        {
            if (!MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), MessageManager.welcomeMessageKey))
            {
                TextChannel welcomeChannel = message.getMentionedChannels().get(0);
                String color = "#ffa500";
                if (args[4].startsWith("#"))
                {
                    color = args[4];
                }

                if (args[3].contains(welcomeChannel.getName()))
                {
                    if (args.length >= 5)
                    {
                        String title = "";
                        String welcomeMessage = "";

                        StringBuilder builder = new StringBuilder();
                        for (int i = 5; i < args.length; i++)
                        {
                            builder.append(args[i] + " ");
                        }
                        String wholeMessage = builder.toString();
                        wholeMessage.trim();

                        if (wholeMessage.contains(messageKey))
                        {
                            String[] messageAndTitle = wholeMessage.split(messageKey);
                            title = messageAndTitle[0];
                            welcomeMessage = messageAndTitle[1];
                        }

                        //SQL
                        MessageManager.sql.MessageAddToSQL(guild.getIdLong(), welcomeChannel.getIdLong(), color, MessageManager.welcomeMessageKey, title, welcomeMessage);

                        //Message
                        MessageManager.embeds.SuccessfullyAddedMessage(channel, MessageManager.welcomeMessageKey);
                    }
                    else
                    {
                        //Error
                        MessageManager.embeds.NoMessageDefinedError(channel, MessageManager.welcomeMessageKey);
                    }
                }
                else
                {
                    //Error
                    MessageManager.embeds.NoMessageChannelMentionedError(channel, MessageManager.welcomeMessageKey);
                }
            }
            else
            {
                //Error
                MessageManager.embeds.MessageAlreadyExistsError(channel, MessageManager.welcomeMessageKey);
            }
        }
        else
        {
            //Usage
            MessageManager.embeds.WelcomeMessageAddUsage(channel);
        }
    }

    private void WelcomeMessageRemoveCommand(TextChannel channel, String[] args)
    {
        Guild guild = channel.getGuild();
        if (args.length == 3)
        {
            //SQL
            MessageManager.sql.MessageRemoveFromSQL(guild.getIdLong(), MessageManager.welcomeMessageKey);

            //Message
            MessageManager.embeds.SuccessfullyRemovedMessage(channel, MessageManager.welcomeMessageKey);
        }
        else
        {
            //Usage
            MessageManager.embeds.WelcomeMessageAddUsage(channel);
        }
    }


}
