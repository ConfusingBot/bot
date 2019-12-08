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
                    if(CommandsUtil.isNumeric(args[3]))
                    {
                        String[] emotes = new String[]{"1️⃣", "2️⃣", "2️⃣"};
                        int maxVotePoints = emotes.length;

                        TextChannel textChannel = mentionedChannels.get(0);
                        List<String> texts = new ArrayList<>();
                        String title = "Vote!";
                        int timeInHours = Integer.parseInt(args[3]);

                        String wholeCommand = "";
                        for (int i = 4; i < args.length; i++)
                        {
                            wholeCommand += (args[i] + " ");
                        }
                        wholeCommand.trim();

                        int index = 1;
                        String text = "";
                        String[] words = wholeCommand.split(" ");
                        boolean addToTexts = false;
                        for (int i = 0; i < words.length; i++)
                        {
                            if (index <= maxVotePoints + 1)
                            {
                                String searchWord = index + ":";
                                if (words[i].equals(searchWord))
                                {
                                    if (addToTexts)
                                    {
                                        text.trim();
                                        texts.add(text);
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
                                    text += (words[i] + " ");
                                }
                            }
                        }
                        texts.add(text);

                        String voteText = "";
                        for(int i = 0; i < texts.size(); i++){
                            voteText += (emotes[i] + " " + texts.get(i) + "\n\n");
                        }

                        //Message
                        long messageID = VoteCommandManager.embeds.SendVoteEmbed(textChannel, title, voteText, timeInHours);

                        for(String emote : emotes){
                            CommandsUtil.reactEmote(emote, channel, messageID, true);
                        }


                        //SQL
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);
                        VoteCommandManager.sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), messageID, timeInHours, creationTime);

                        System.out.println("Points: " + texts + " Title: " + title + " TextChannel: " + textChannel + " Time: " + timeInHours + " CreationTime: " + creationTime);
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
}
