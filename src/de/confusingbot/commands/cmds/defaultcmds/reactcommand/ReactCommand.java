package de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.ArrayList;
import java.util.List;

public class ReactCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public ReactCommand()
    {
        embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;
    Permission MESSAGE_ADD_REACTION = Permission.MESSAGE_ADD_REACTION;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //args[0]  args[1]  args[2]       args[3]
        //- react [channel] [message id] [emotjis]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (bot.hasPermission(channel, MESSAGE_ADD_REACTION))
            {
                if (args.length > 3)
                {
                    List<String> probablyEmote = new ArrayList<>();
                    for (int i = 3; i < args.length; i++)
                    {
                        probablyEmote.add(args[i]);
                    }

                    List<TextChannel> channels = message.getMentionedChannels();
                    List<String> emotes = CommandsUtil.getEmotes(message, probablyEmote);

                    if (!channels.isEmpty())
                    {
                        TextChannel textChannel = message.getMentionedChannels().get(0);
                        String messageIDString = args[2];
                        ArrayList<String> noValidEmotes = new ArrayList<>();
                        ArrayList<String> validEmotes = new ArrayList<>();

                        if (CommandsUtil.isNumeric(messageIDString))
                        {
                            long messageID = Long.parseLong(messageIDString);

                            for (String emoteString : emotes)
                            {
                                if (!emoteString.isEmpty() && emoteString != null)
                                {
                                    boolean success = CommandsUtil.reactEmote(emoteString, textChannel, messageID, true);

                                    if (success)
                                        validEmotes.add(emoteString);
                                    else
                                        noValidEmotes.add(emoteString);
                                }
                                else
                                {
                                    //Error
                                    embeds.YouHaveNotMentionedAValidEmoteError(channel);
                                }
                            }

                            if (validEmotes.size() > 0)
                            {
                                //Message
                                embeds.SuccessfullyAddedEmotes(channel, validEmotes, noValidEmotes);
                            }
                            else
                            {
                                //Error
                                embeds.YouHaveNotMentionedAValidEmoteError(channel);
                            }
                        }
                        else
                        {
                            //Error
                            embeds.ThisIsNoMessageIDError(channel, messageIDString);
                        }
                    }
                    else
                    {
                        embeds.YouHaveNotMentionedATextChannelError(channel);
                    }
                }
                else
                {
                    //Usage
                    embeds.ReactCommandUsage(channel);
                }
            }
            else
            {
                //Error
                EmbedManager.SendNoPermissionEmbed(channel, MESSAGE_ADD_REACTION, "");
            }
        }
    }
}
