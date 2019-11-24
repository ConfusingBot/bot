package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.lang.reflect.Array;
import java.nio.channels.Channel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReactRolesCommand implements ServerCommand
{

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //By errors watch this: https://www.youtube.com/watch?v=68JMMzjZhJI
        //   args[0]   args[1]    args[2]     args[3]      args[4]   args[5]
        //- reactrole    add     [channel]  [message id]   [emotjis]  [Rolle]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        //Check Permission
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
                        removeCommand(message, args, channel);
                        break;
                    case "list":
                        ListCommand(channel.getGuild(), channel);
                        break;
                    default:
                        //Usage
                        ReactRoleManager.embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                ReactRoleManager.embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            ReactRoleManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void ListCommand(Guild guild, TextChannel channel)
    {
        List<Long> reactrolesIds = new ArrayList<>();
        List<Long> messageIDs = new ArrayList<>();
        List<Long> channelIDs = new ArrayList<>();
        List<String> emoteStrings = new ArrayList<>();

        try
        {
            ResultSet set = ReactRoleManager.sql.GetReactRolesResultSet(guild.getIdLong());

            while (set.next())
            {
                reactrolesIds.add(set.getLong("roleid"));
                messageIDs.add(set.getLong("messageid"));
                channelIDs.add(set.getLong("channelid"));
                emoteStrings.add(set.getString("emote"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (reactrolesIds.size() != 0 && reactrolesIds != null)
        {
            //Create Description -> all voice channel
            String description = "";
            for (int i = 0; i < reactrolesIds.size(); i++)
            {
                Role role = guild.getRoleById(reactrolesIds.get(i));
                TextChannel roleAddChannel = guild.getTextChannelById(channelIDs.get(i));
                String emoteString = emoteStrings.get(i);
                long messageID = messageIDs.get(i);
                if (role != null && roleAddChannel != null && getLatestMessages(roleAddChannel).contains(messageID))
                {
                    if (CommandsUtil.isNumeric(emoteString))
                        emoteString = guild.getEmoteById(emoteString).getAsMention();

                    description += "" + emoteString + "   " + role.getAsMention() + "   in" + roleAddChannel.getAsMention() + "\n\n";
                }
                else
                {
                    description += "⚠️**ReactRole does not exists!**\n";
                    //SQL
                    ReactRoleManager.sql.removeFromSQL(guild.getIdLong(), channelIDs.get(i), messageID, emoteString, reactrolesIds.get(i));
                }
            }

            //Message
            ReactRoleManager.embeds.SendReactRoleListEmbed(channel, description);
        }
        else
        {
            ReactRoleManager.embeds.HasNoReactRoleInformation(channel);
        }
    }

    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 6)
        {
            List<TextChannel> channels = message.getMentionedChannels();
            List<Role> roles = message.getMentionedRoles();

            if (!channels.isEmpty() && !roles.isEmpty())
            {
                TextChannel textChannel = channels.get(0);//args 2
                Role role = roles.get(0);//args 5
                String messageIDString = args[3]; //args 3
                String emoteString = CommandsUtil.getEmotes(message, Arrays.asList(args[4])).get(0);

                if (CommandsUtil.isNumeric(messageIDString))
                {
                    long messageID = Long.parseLong(messageIDString);

                    if (!emoteString.isEmpty() && emoteString != null)
                    {
                        if (!ReactRoleManager.sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                        {
                            CommandsUtil.reactEmote(emoteString, channel, messageID, true);

                            //SQL
                            ReactRoleManager.sql.addToSQL(message.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, role.getIdLong());

                            //Message
                            ReactRoleManager.embeds.SuccessfullyAddedReactRole(channel, role);
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.ReactRoleAlreadyExistsError(channel);
                        }
                    }
                    else
                    {
                        //Error
                        ReactRoleManager.embeds.YouHaveNotMentionedAValidEmoteError(channel);
                    }
                }
                else
                {
                    //Error
                    ReactRoleManager.embeds.NoMessageIDError(channel, messageIDString);
                }
            }
            else
            {
                //Usage
                ReactRoleManager.embeds.AddUsage(channel);
            }
        }
        else
        {
            //Usage
            ReactRoleManager.embeds.AddUsage(channel);
        }
    }

    private void removeCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length >= 6)
        {
            List<TextChannel> channels = message.getMentionedChannels();
            List<Role> roles = message.getMentionedRoles();

            if (!channels.isEmpty() && !roles.isEmpty())
            {
                Role role = roles.get(0);
                String messageIDString = args[3];
                String emoteString = CommandsUtil.getEmotes(message, Arrays.asList(args[4])).get(0);

                if (CommandsUtil.isNumeric(messageIDString))
                {
                    long messageID = Long.parseLong(messageIDString);

                    if (!emoteString.isEmpty() && emoteString != null)
                    {
                        if (ReactRoleManager.sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                        {
                            //React
                            CommandsUtil.reactEmote(emoteString, channel, messageID, false);

                            //SQL
                            ReactRoleManager.sql.removeFromSQL(message.getGuild().getIdLong(), channel.getIdLong(), messageID, emoteString, role.getIdLong());

                            //Message
                            ReactRoleManager.embeds.SuccessfullyRemovedReactRole(channel, role);
                        }
                        else
                        {
                            //Error
                            ReactRoleManager.embeds.ReactRoleNotExistsError(channel);
                        }
                    }
                    else
                    {
                        ReactRoleManager.embeds.YouHaveNotMentionedAValidEmoteError(channel);
                    }
                }
                else
                {
                    //Error
                    ReactRoleManager.embeds.NoMessageIDError(channel, messageIDString);
                }
            }
            else
            {
                //Usage
                ReactRoleManager.embeds.RemoveUsage(channel);
            }
        }
        else
        {
            //Usage
            ReactRoleManager.embeds.AddUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private List<Long> getLatestMessages(MessageChannel channel)
    {
        CommandsUtil.sleepXSeconds(0.5f);
        List<Long> messages = new ArrayList<>();

        for (Message message : channel.getIterableHistory().cache(false))
        {
            messages.add(message.getIdLong());
        }
        return messages;
    }
}

