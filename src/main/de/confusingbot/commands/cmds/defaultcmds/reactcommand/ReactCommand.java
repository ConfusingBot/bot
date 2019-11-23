package main.de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.exceptions.ContextException;

import java.util.ArrayList;
import java.util.List;

public class ReactCommand implements ServerCommand
{

    Embeds embeds = new Embeds();


    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {

        //args[0]  args[1]  args[2]       args[3]
        //- react [channel] [message id] [emotjis]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

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

                if (CommandsUtil.isNumeric(messageIDString))
                {
                    long messageID = Long.parseLong(messageIDString);

                    for (String emoteString : emotes)
                    {
                        if (!emoteString.isEmpty() && emoteString != null)
                        {
                            CommandsUtil.reactEmote(emoteString, textChannel, messageID, true);
                        }
                        else
                        {
                            //Error
                            embeds.YouHaveNotMentionedAValidEmoteError(channel);
                        }
                    }
                    //Message
                    embeds.SuccessfullyAddedEmotes(channel);
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
}
