package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VoteCommand implements ServerCommand
{


    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //vote create [#channel] [time in hours] [Header] 1: text1 2: text2 3: text3
        //vote remove messageid


        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {

            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "create":
                        CreateCommand(args, member, guild, channel, message);
                        break;
                    case "remove":

                        break;
                    default:
                        //Usage
                        VoteCommandManager.embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                VoteCommandManager.embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            VoteCommandManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void CreateCommand(String[] args, Member member, Guild guild, TextChannel channel, Message message)
    {
        if (args.length > 6)
        {
            String channelString = args[2];
            List<TextChannel> mentionedChannels = message.getMentionedChannels();

            if (mentionedChannels.size() > 0)
            {
                if (channelString.contains(mentionedChannels.get(0).getName()))
                {
                    if (CommandsUtil.isNumeric(args[3]))
                    {
                        String[] voteEmotes = new String[]{"1️⃣", "2️⃣", "2️⃣"};
                        int maxVotePoints = voteEmotes.length;

                        TextChannel textChannel = mentionedChannels.get(0);
                        String title = "Vote!";
                        int timeInHours = Integer.parseInt(args[3]);

                        String wholeCommand = "";
                        for (int i = 4; i < args.length; i++)
                        {
                            wholeCommand += (args[i] + " ");
                        }
                        wholeCommand.trim();

                        List<String> voteTexts = new ArrayList<>();
                        int index = 1;
                        String text = "";
                        String[] wholeCommandWords = wholeCommand.split(" ");
                        boolean addToTexts = false;
                        for (int i = 0; i < wholeCommandWords.length; i++)
                        {
                            if (index <= maxVotePoints + 1)
                            {
                                String searchWord = index + ":";
                                if (wholeCommandWords[i].equals(searchWord))
                                {
                                    if (addToTexts)
                                    {
                                        text.trim();
                                        voteTexts.add(text);
                                    }
                                    else
                                    {
                                        if (!text.isEmpty())
                                            title = text;
                                    }
                                    text = "";
                                    index++;

                                    addToTexts = true;
                                }
                                else
                                {
                                    text += (wholeCommandWords[i] + " ");
                                }
                            }
                        }
                        voteTexts.add(text);

                        List<String> usedEmotes = new ArrayList<>();
                        String voteText = "";
                        for (int i = 0; i < voteTexts.size(); i++)
                        {
                            voteText += (voteEmotes[i] + " " + voteTexts.get(i) + "\n\n");
                            usedEmotes.add(voteEmotes[i]);
                        }

                        //Message
                        long messageID = VoteCommandManager.embeds.SendVoteEmbed(textChannel, title, voteText, timeInHours);

                        for (String emote : voteEmotes)
                        {
                            CommandsUtil.reactEmote(emote, channel, messageID, true);
                        }

                        //SQL
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);
                        VoteCommandManager.sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), messageID, timeInHours, creationTime, buildEmoteString(usedEmotes));

                        System.out.println("Points: " + voteTexts + " Title: " + title + " TextChannel: " + textChannel + " Time: " + timeInHours + " CreationTime: " + creationTime);
                    }
                    else
                    {
                        VoteCommandManager.embeds.NoMentionedTimeInHours(channel);
                    }
                }
                else
                {
                    VoteCommandManager.embeds.NoMentionedTextChannelError(channel);
                }
            }
            else
            {
                VoteCommandManager.embeds.NoMentionedTextChannelError(channel);
            }
        }
        else
        {
            //Usage
            VoteCommandManager.embeds.CreateUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private String buildEmoteString(List<String> emotes)
    {
        StringBuilder builder = new StringBuilder();
        for (String emote : emotes)
        {
            builder.append(emote + " ");
        }
        return builder.toString().trim();
    }
}
