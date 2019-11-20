package main.de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;

public class ReactRolesCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

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
        if (args.length >= 5)
        {
            List<TextChannel> channels = message.getMentionedChannels();
            List<Role> roles = message.getMentionedRoles();

            if (!channels.isEmpty() && !roles.isEmpty())
            {
                TextChannel textChannel = channels.get(0);//args 2
                Role role = roles.get(0);//args 5
                String messageIDString = args[3]; //args 3
                String emoteString = getEmote(message, args);

                try
                {
                    long messageID = Long.parseLong(messageIDString);

                    if (!sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                    {
                        reactEmote(emoteString, channel, messageID, true);

                        //SQL
                        sql.addToSQL(message.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, role.getIdLong());

                        //Message
                        embeds.SuccessfullyAddedReactRole(channel, role);
                    }
                    else
                    {
                        //Error
                        embeds.ReactRoleAlreadyExistsError(channel);
                    }

                } catch (NumberFormatException e)
                {
                    //Error
                    embeds.NoMessageIDError(channel, messageIDString);
                }
            }
            else
            {
                //Usage
                embeds.AddUsage(channel);
            }
        }
        else
        {
            //Usage
            embeds.AddUsage(channel);
        }

    }

    private void removeCommand(Message message, String[] args, TextChannel channel)
    {
        List<TextChannel> channels = message.getMentionedChannels();
        List<Role> roles = message.getMentionedRoles();

        if (!channels.isEmpty() && !roles.isEmpty())
        {
            Role role = roles.get(0);
            String messageIDString = args[3];
            String emoteString = getEmote(message, args);

            try
            {
                long messageID = Long.parseLong(messageIDString);
                if (sql.ExistsInSQL(message.getGuild().getIdLong(), messageID, emoteString, role.getIdLong()))
                {
                    reactEmote(emoteString, channel, messageID, false);

                    //SQL
                    sql.removeFromSQL(message.getGuild().getIdLong(), channel.getIdLong(), messageID, emoteString, role.getIdLong());

                    //Message
                    embeds.SuccessfullyRemovedReactRole(channel, role);
                }
                else
                {
                    //Error
                    embeds.ReactRoleNotExistsError(channel);
                }

            } catch (NumberFormatException e)
            {
                //Error
                embeds.NoMessageIDError(channel, messageIDString);
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
    private String getEmote(Message message, String[] args)
    {
        List<Emote> emotes = message.getEmotes();

        String emoteString = "";
        if (!emotes.isEmpty())
        {
            Emote emote = emotes.get(0);
            emoteString += emote.getIdLong();
        }
        else
        {
            String emote = args[4];
            emoteString += emote;
        }
        return emoteString;
    }

    private static void reactEmote(String emoteString, TextChannel channel, long messageid, boolean add)
    {
        if (CommandsUtil.isNumeric(emoteString))//if emoteString is a emoteID
        {
            Emote emote = channel.getGuild().getEmoteById(Long.parseLong(emoteString));
            if (add)
                channel.addReactionById(messageid, emote).queue();
            else
                channel.removeReactionById(messageid, emote).queue();
        }
        else
        {
            if (add)
                channel.addReactionById(messageid, emoteString).queue();
            else
                channel.removeReactionById(messageid, emoteString).queue();
        }
    }


}

