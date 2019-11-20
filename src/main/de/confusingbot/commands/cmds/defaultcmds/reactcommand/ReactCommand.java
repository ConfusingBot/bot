package main.de.confusingbot.commands.cmds.defaultcmds.reactcommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

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
            List<TextChannel> channels = message.getMentionedChannels();
            List<Emote> emotes = message.getEmotes();

            if (!channels.isEmpty())
            {
                TextChannel textChannel = message.getMentionedChannels().get(0);
                String messageIDString = args[2];

                try
                {
                    long messageID = Long.parseLong(messageIDString);
                    List<String> customemotes = new ArrayList<>();

                    String emotesString = "";

                    for (Emote emote : emotes)
                    {
                        textChannel.addReactionById(messageID, emote).queue();
                        customemotes.add(":" + emote.getName() + ":");

                        emotesString += ":" + emote.getName() + ":";
                    }

                    for (int i = 3; i < args.length; i++)
                    {
                        String emote = args[i];
                        if (!customemotes.contains(emote))
                        {
                            textChannel.addReactionById(messageID, args[i]).queue();
                        }
                    }

                    //Message
                    embeds.SuccessfullyAddedEmotes(channel, emotesString);

                } catch (NumberFormatException e)
                {
                    //Usage
                    embeds.ThisIsNoMessageIDError(channel, messageIDString);
                }
            }
        }
        else
        {
            //Usage
            embeds.ReactCommandUsage(channel);
        }
    }
}
