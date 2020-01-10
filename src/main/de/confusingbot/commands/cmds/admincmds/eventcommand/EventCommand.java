package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleManager;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageManager;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ListReactRolesRunnable;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRoleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventCommand implements ServerCommand
{

    Embeds embeds = EventCommandManager.embeds;
    SQL sql = EventCommandManager.sql;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //#hex
        //Event yeah #cool #fff
        //- event create [#channel] [messageid] [color] [time] [takePartEmote] [eventName] ROLE:[eventRoleName]
        //- event remove [@eventRole]

        //SQL: event -> channelId messageID #color 5 eventroleID emote

        //- event announcement [#channel] [@eventRole] [Title] MESSAGE: [Message]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "create":
                        CreateCommand(message, args, channel);
                        break;
                    case "remove":
                        RemoveCommand(message, args, channel);
                        break;
                    case "list":
                        ListCommand(channel);
                        break;
                    case "announcement":
                        AnnouncementCommand(channel, message, args);
                        break;
                    default:
                        //Usage
                        AcceptRuleManager.embeds.GeneralUsage(channel);
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
    private void ListCommand(TextChannel channel)
    {
        long messageid = ReactRoleManager.embeds.SendWaitMessage(channel);
        Runnable r = new ListEventsRunnable(channel.getGuild(), channel, messageid);
        Thread t = new Thread(r);
        t.start();
    }

    private void AnnouncementCommand(TextChannel channel, Message message, String[] args)
    {
        if (args.length >= 5)
        {
            List<TextChannel> channels = message.getMentionedChannels();
            List<Role> roles = message.getMentionedRoles();
            Guild guild = channel.getGuild();

            if (!channels.isEmpty() && ("#" + channels.get(0).getName()).equals(args[2]))
            {
                if (!roles.isEmpty() && ("@" + roles.get(0).getName()).equals(args[3]))
                {
                    if (sql.eventRoleExist(guild.getIdLong(), roles.get(0).getIdLong()))
                    {
                        //Get Message and Title
                        String wholeMessage = getWholeMessage(args, 4);
                        String title = "Event Announcement!";
                        String shownMessage = "";

                        if (wholeMessage.contains(EventCommandManager.messageStartKey))
                        {
                            String[] messageAndTitle = wholeMessage.split(EventCommandManager.messageStartKey);
                            title = messageAndTitle[0];
                            shownMessage = messageAndTitle[1];
                        }
                        else
                        {
                            shownMessage = wholeMessage;
                        }

                        //Add eventRole as mentioned
                        shownMessage += ("\n\n" + roles.get(0).getAsMention());

                        //Message
                        embeds.SendAnnouncement(title, shownMessage, roles.get(0).getColor(), channels.get(0));
                    }
                    else
                    {
                        //Error
                        embeds.NoMentionedEventRoleError(channel);
                    }
                }
                else
                {
                    //Error
                    embeds.NoMentionedRoleError(channel);
                }
            }
            else
            {
                //Error
                embeds.NoMentionedTextChannelError(channel);
            }
        }
        else
        {
            //Usage
            embeds.AnnouncementUsage(channel);
        }
    }

    private void CreateCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 9)
        {
            Guild guild = message.getGuild();
            String channelName = args[2];
            String messageIdString = args[3];
            long messageID = -1;
            String colorString = args[4];
            String timeString = args[5];
            int time = -1;
            String emoteString = CommandsUtil.getEmote(message, args[6]);
            String eventName = "empty";
            String roleName = "empty";

            List<TextChannel> mentionedChannels = message.getMentionedChannels();

            if (!mentionedChannels.isEmpty() && channelName.contains(mentionedChannels.get(0).getName()))
            {
                TextChannel textChannel = mentionedChannels.get(0);

                if (CommandsUtil.isNumeric(messageIdString))
                {
                    messageID = Long.parseLong(messageIdString);
                    if (CommandsUtil.isColor(colorString))
                    {
                        if (CommandsUtil.isNumeric(timeString))
                        {
                            time = Integer.parseInt(timeString);

                            String nameString = "";
                            for (int i = 7; i < args.length; i++)
                            {
                                nameString += args[i] + " ";
                            }

                            String[] nameParts = nameString.split("ROLE:");

                            if (nameParts.length == 2)
                            {
                                eventName = nameParts[0];
                                roleName = nameParts[1];

                                if (CommandsUtil.reactEmote(emoteString, textChannel, messageID, true))
                                {
                                    //TODO Wait Message

                                    //Create Role
                                    RoleAction roleAction = guild.createRole();
                                    roleAction.setName(roleName);
                                    roleAction.setColor(Color.decode(colorString));
                                    roleAction.setMentionable(true);
                                    roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
                                    long finalMessageID = messageID;
                                    int finalTime = time;
                                    String finalEventName = eventName;
                                    roleAction.queue(role -> {
                                        //Get CurrentTime
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                        String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);
                                        //SQL
                                        sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), finalMessageID, role.getIdLong(), finalTime, colorString, emoteString, creationTime, finalEventName);

                                        //Message
                                        embeds.SuccessfullyAddedEvent(channel, Color.decode(colorString), finalEventName);
                                    });
                                }
                                else
                                {
                                    //Error
                                    embeds.NoValidEmoteError(channel, emoteString);
                                }
                            }
                            else
                            {
                                //Error
                                embeds.NoMentionedNamesError(channel);
                            }
                        }
                    }
                    else
                    {
                        //Error
                        embeds.NoSelectedColorError(channel);
                    }
                }
                else
                {
                    //Error
                    embeds.NoMentionedMessageIDError(channel);
                }
            }
            else
            {
                //Error
                embeds.NoMentionedTextChannelError(channel);
            }
        }
        else
        {
            //Usage
            embeds.CreateUsage(channel);
        }
    }

    private void RemoveCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 3)
        {
            List<Role> mentionedRoles = message.getMentionedRoles();
            Guild guild = message.getGuild();

            if (!mentionedRoles.isEmpty())
            {
                Role role = mentionedRoles.get(0);

                if (sql.existsInSQL(guild.getIdLong(), role.getIdLong()))
                {
                    String colorString = sql.getEventColor(guild.getIdLong(), role.getIdLong());
                    Color color = Color.decode(colorString);
                    String eventName = sql.getEventName(guild.getIdLong(), role.getIdLong());

                    //SQL
                    sql.removeFormSQL(guild.getIdLong(), role.getIdLong());

                    //Message
                    embeds.SuccessfullyRemovedEvent(channel, color, eventName);
                }
                else
                {
                    //Error
                    embeds.NoExistingEventRoleError(channel, role.getAsMention());
                }
            }
            else
            {
                //Error
                embeds.NoMentionedRoleError(channel);
            }
        }
        else
        {
            //Usage
            embeds.RemoveUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
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
