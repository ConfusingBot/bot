package main.de.confusingbot.commands.cmds.admincmds.eventcommand;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleManager;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRoleManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.requests.restaction.RoleAction;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

public class EventCommand implements ServerCommand
{

    Embeds embeds = EventCommandManager.embeds;
    SQL sql = EventCommandManager.sql;

    public EventCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MANAGE_ROLES = Permission.MANAGE_ROLES;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //#hex
        //Event yeah #cool #fff
        //- event create [#channel] [messageid] [color] [time] [takePartEmote] [eventName] ROLE:[eventRoleName]
        //- event remove [@eventRole]

        //SQL: event -> channelId messageID #color 5 eventroleID emote

        //- event announcement [#channel] [@eventRole] [Title] MESSAGE: [Message]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (member.hasPermission(channel, EventCommandManager.permission))
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
                embeds.NoPermissionError(channel, EventCommandManager.permission);
            }
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
                    Role role = roles.get(0);
                    if (sql.eventRoleExist(guild.getIdLong(), role.getIdLong()))
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
                        shownMessage += ("\n\n" + role.getAsMention());

                        //Message
                        embeds.SendAnnouncement(title, shownMessage, role.getColor(), channels.get(0));
                    }
                    else
                    {
                        //Error
                        embeds.NoMentionedEventRoleError(channel, role.getAsMention());
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
        if (bot.hasPermission(channel, MANAGE_ROLES))
        {
            if (args.length >= 7)
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
                boolean hasColor = true;
                List<TextChannel> mentionedChannels = message.getMentionedChannels();

                if (!mentionedChannels.isEmpty() && channelName.contains(mentionedChannels.get(0).getName()))
                {
                    TextChannel textChannel = mentionedChannels.get(0);

                    if (CommandsUtil.isNumeric(messageIdString))
                    {
                        messageID = Long.parseLong(messageIdString);
                        if (!CommandsUtil.isColor(colorString))
                        {
                            hasColor = false;
                            emoteString = timeString;
                            timeString = colorString;
                            colorString = "#fffff";
                        }

                        if (!emoteString.isEmpty() && emoteString != null)
                        {
                            if (CommandsUtil.isNumeric(timeString))
                            {
                                time = Integer.parseInt(timeString);

                                String nameString = "";
                                for (int i = hasColor ? 7 : 6; i < args.length; i++)
                                {
                                    nameString += args[i] + " ";
                                }

                                String[] nameParts = nameString.split("ROLE:");

                                if (nameParts.length == 2 || nameParts.length == 1)
                                {
                                    if (nameParts.length == 2)
                                    {
                                        eventName = nameParts[0];
                                        roleName = nameParts[1];
                                    }
                                    else
                                    {
                                        eventName = nameParts[0];
                                        roleName = nameParts[0];
                                    }

                                    boolean messageIdExists = CommandsUtil.messageIdExists(textChannel, messageID);
                                    if (messageIdExists)
                                    {


                                        if (CommandsUtil.reactEmote(emoteString, textChannel, messageID, true))
                                        {
                                            //Create Role
                                            RoleAction roleAction = guild.createRole();
                                            roleAction.setName(roleName);
                                            roleAction.setColor(Color.decode(colorString));
                                            roleAction.setMentionable(true);
                                            roleAction.setPermissions(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.VOICE_CONNECT);
                                            long finalMessageID = messageID;
                                            int finalTime = time;
                                            String finalEventName = eventName;
                                            String finalColorString = colorString;
                                            String finalEmoteString = emoteString;
                                            roleAction.queue(role -> {

                                                //Get CurrentTime
                                                String creationTime = OffsetDateTime.now().toLocalDateTime().format(CommandsUtil.formatter);
                                                String endTime = CommandsUtil.AddXTime(OffsetDateTime.now().toLocalDateTime(), finalTime, true).format(CommandsUtil.formatter);
                                                //SQL
                                                sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), finalMessageID, role.getIdLong(), finalColorString, finalEmoteString, finalEventName, endTime, creationTime);

                                                //Message
                                                embeds.SuccessfullyAddedEvent(channel, Color.decode(finalColorString), finalEventName);
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
                                        embeds.NoValidIdError(channel, messageIdString);
                                    }
                                }
                                else
                                {
                                    //Error
                                    embeds.NoMentionedNamesError(channel);
                                }
                            }
                            else
                            {
                                embeds.noMentionedTimeError(channel);
                            }
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
                        embeds.NoValidIdError(channel, messageIdString);
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
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
        }
    }

    private void RemoveCommand(Message message, String[] args, TextChannel channel)
    {
        if (bot.hasPermission(channel, MANAGE_ROLES))
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
                        long eventMessageId = sql.getEventMessageId(guild.getIdLong(), role.getIdLong());
                        long channelId = sql.getEventChannelId(guild.getIdLong(), role.getIdLong());
                        TextChannel textChannel = guild.getTextChannelById(channelId);

                        if (textChannel != null)
                        {
                            //Add Cross Emote
                            CommandsUtil.reactEmote("❌", textChannel, eventMessageId, true);
                        }

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
        else
        {
            //Error
            EmbedManager.SendNoPermissionEmbed(channel, MANAGE_ROLES, "");
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

    private String getMentionableMessage(String
                                                 message, List<TextChannel> mentionedChannel, List<Role> mentionedRoles, List<User> mentionedUsers)
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
