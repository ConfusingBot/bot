package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MessageCommand implements ServerCommand
{
    public MessageCommand()
    {
        MessageManager.embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- message add welcome (private) [#channel] ([#hexcolor]) ([titleExample]) MESSAGE: Welcome @newMember to the server look at #rule
        //- message remove welcome

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(MessageManager.permission))
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
                                        MessageAddCommand(channel, args, message, MessageManager.welcomeMessageKey, args.length > 3 && args[3].equals("private"));
                                        break;
                                    case "leave":
                                        MessageAddCommand(channel, args, message, MessageManager.leaveMessageKey, args.length > 3 && args[3].equals("private"));
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
                                        MessageRemoveCommand(channel, args, MessageManager.welcomeMessageKey, args.length > 3 && args[3].equals("private"));
                                        break;
                                    case "leave":
                                        MessageRemoveCommand(channel, args, MessageManager.leaveMessageKey, args.length > 3 && args[3].equals("private"));
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
                MessageManager.embeds.NoPermissionError(channel, MessageManager.permission);
            }
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void MessageAddCommand(TextChannel channel, String[] args, Message message, String messageKey, boolean isPrivate)
    {
        if (messageKey.equals(MessageManager.leaveMessageKey) && isPrivate)
        {
            MessageManager.embeds.CanNotSendPrivateLeaveMessage(channel);
            return;
        }


        Guild guild = channel.getGuild();
        if (args.length > 4)
        {
            if (!MessageManager.sql.MessageExistsInSQL(guild.getIdLong(), messageKey, isPrivate))
            {

                List<TextChannel> mentionedDiscordChannel = message.getMentionedChannels();

                if (mentionedDiscordChannel.size() > 0 || (isPrivate && mentionedDiscordChannel.size() == 0))
                {
                    //Get channel where to send the message
                    TextChannel messageChannel = null;
                    List<TextChannel> mentionedChannel = mentionedDiscordChannel.stream().collect(Collectors.toList());
                    if (!isPrivate)
                    {
                        messageChannel = mentionedDiscordChannel.get(0);
                        mentionedChannel.remove(0);
                    }

                    List<Role> mentionedRoles = message.getMentionedRoles();
                    List<User> mentionedUser = message.getMentionedUsers();

                    if ((isPrivate && messageChannel == null) || (messageChannel != null && args[3].contains(messageChannel.getName())))
                    {
                        //Get Color
                        String defaultColor = "#ffa500";
                        String color = defaultColor;
                        if (args[4].startsWith("#") && ColorExists(args[4]))
                        {
                            color = args[4];
                        }

                        boolean hasChosenNewColor = !color.equals(defaultColor);
                        if ((args.length > 4 && !hasChosenNewColor) || args.length > 5)
                        {
                            int startIndex = hasChosenNewColor ? 5 : 4;

                            String wholeMessage = getWholeMessage(args, startIndex);
                            String title = " ";
                            String shownMessage = " ";

                            if (wholeMessage.contains(MessageManager.messageStartKey))
                            {
                                String[] messageAndTitle = wholeMessage.split(MessageManager.messageStartKey);
                                title = messageAndTitle[0];
                                shownMessage = messageAndTitle[1];
                            }
                            else
                            {
                                shownMessage = wholeMessage;
                            }

                            //SQL
                            MessageManager.sql.MessageAddToSQL(
                                    guild.getIdLong(),
                                    !isPrivate ? messageChannel.getIdLong() : -1,
                                    color,
                                    messageKey,
                                    title,
                                    getMentionableMessage(shownMessage, mentionedChannel, mentionedRoles, mentionedUser),
                                    isPrivate
                            );

                            //Message
                            MessageManager.embeds.SuccessfullyAddedMessage(channel, messageKey, isPrivate);
                        }
                        else
                        {
                            //Error
                            MessageManager.embeds.NoMessageDefinedError(channel, messageKey);
                        }
                    }
                    else
                    {
                        //Error
                        MessageManager.embeds.NoMessageChannelMentionedError(channel, messageKey);
                    }
                }
                else
                {
                    //Error
                    MessageManager.embeds.NoMessageChannelMentionedError(channel, messageKey);
                }
            }
            else
            {
                //Error
                MessageManager.embeds.MessageAlreadyExistsError(channel, messageKey);
            }
        }
        else
        {
            //Usage
            MessageManager.embeds.MessageAddUsage(channel, messageKey, isPrivate);
        }
    }

    private void MessageRemoveCommand(TextChannel channel, String[] args, String messageKey, boolean isPrivate)
    {
        Guild guild = channel.getGuild();
        if (args.length >= 3)
        {
            if (MessageManager.sql.MessageExistsInSQL(channel.getGuild().getIdLong(), messageKey, isPrivate))
            {
                //SQL
                MessageManager.sql.MessageRemoveFromSQL(guild.getIdLong(), messageKey, isPrivate);

                //Message
                MessageManager.embeds.SuccessfullyRemovedMessage(channel, messageKey, isPrivate);
            }
            else
            {
                //Error
                MessageManager.embeds.MessageDoesNotExistsError(channel, messageKey);
            }
        }
        else
        {
            //Usage
            MessageManager.embeds.MessageRemoveUsage(channel, messageKey);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private boolean ColorExists(String hexColor)
    {
        try
        {
            Color color = Color.decode(hexColor);
            return true;
        } catch (NumberFormatException e)
        {

        }
        return false;
    }

    private String getWholeMessage(String[] args, int startIndex)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < args.length; i++)
        {
            builder.append(args[i] + " ");
        }
        String wholeMessage = builder.toString();
        return wholeMessage.trim();
    }

    private String getMentionableMessage(String message, List<TextChannel> mentionedChannel, List<Role> mentionedRoles, List<User> mentionedUsers)
    {
        String mentionableMessage = "";
        StringBuilder builder = new StringBuilder();
        String[] words = message.split(" ");

        for (String word : words)
        {
            for (TextChannel channel : mentionedChannel)
            {
                if (word.contains(channel.getName()))
                    word = channel.getAsMention();
            }

            for (Role role : mentionedRoles)
            {
                if (word.contains(role.getName()))
                    word = role.getAsMention();
            }

            for (User user : mentionedUsers)
            {
                if (word.contains(user.getName()))
                    word = user.getAsMention();
            }

            builder.append(word + " ");
        }

        mentionableMessage = builder.toString().trim();


        return mentionableMessage;
    }


}
