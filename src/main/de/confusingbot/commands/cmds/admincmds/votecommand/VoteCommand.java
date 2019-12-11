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
                        CreateCommand(args, guild, channel, message);
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
    private void CreateCommand(String[] args, Guild guild, TextChannel channel, Message message)
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
                        TextChannel textChannel = mentionedChannels.get(0);
                        String title = "Vote!";
                        int timeInHours = Integer.parseInt(args[3]);
                        List<String> voteStrings = getVoteStrings(args, 4);

                        //Get title out of voteStrings
                        if(voteStrings.get(0).startsWith("TITLE: ")){
                            title = voteStrings.get(0);
                            title = title.replace("TITLE:", "");
                            title.trim();
                            voteStrings.remove(0);
                        }

                        List<String> usedEmotes = new ArrayList<>();
                        String voteText = "";
                        for (int i = 0; i < voteStrings.size(); i++)
                        {
                            voteText += (VoteCommandManager.voteEmotes[i] + " " + voteStrings.get(i) + "\n\n");
                            usedEmotes.add(VoteCommandManager.voteEmotes[i]);
                        }

                        //Message
                        long messageID = VoteCommandManager.embeds.SendVoteEmbed(textChannel, title, voteText, timeInHours);

                        //React with Emotes
                        for (String emote : usedEmotes)
                        {
                            CommandsUtil.reactEmote(emote, channel, messageID, true);
                        }

                        //Get CurrentTime
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);

                        //SQL
                        VoteCommandManager.sql.addToSQL(guild.getIdLong(), textChannel.getIdLong(), messageID, title, timeInHours, creationTime, buildEmoteString(usedEmotes));
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
    //Help
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

    private List<String> getVoteStrings(String[] args, int startIndex){

        //Build wholeString out of args after the start Index.. so where the Title and the votes comes
        String wholeString = "";
        for (int i = startIndex; i < args.length; i++)
        {
            wholeString += (args[i] + " ");
        }
        wholeString.trim();

        //TODO make this easier
        String[] wholeStringWords = wholeString.split(" ");
        List<String> voteTexts = new ArrayList<>();
        int voteMarkNumber = 1;
        String text = "";

        boolean isNoTitle = false;
        boolean hasTitle = false;

        //Seperate the votes and the title
        for (int i = 0; i < wholeStringWords.length; i++)
        {
                String searchWord = voteMarkNumber + ":";
                if (wholeStringWords[i].equals(searchWord))
                {
                    if (isNoTitle)
                    {
                        text.trim();
                        voteTexts.add(text);
                    }
                    else
                    {
                        if (!text.isEmpty()){
                            voteTexts.add("TITLE: " + text);
                            hasTitle = true;
                        }
                    }
                    text = "";
                    voteMarkNumber++;

                    isNoTitle = true;
                }
                else
                {
                    text += (wholeStringWords[i] + " ");
                }
        }
        text.trim();
        voteTexts.add(text);

        //Calculate ListSize
        int allowedListSize = VoteCommandManager.voteEmotes.length;
        if(hasTitle) allowedListSize += 1;
        if(voteTexts.size() < VoteCommandManager.voteEmotes.length ){
            allowedListSize = voteTexts.size();
        }

        return perepareListSize(voteTexts, allowedListSize);
    }

    private List<String> perepareListSize(List<String> list, int size){
        List<String> newList = new ArrayList<>();
        for(int i = 0; i < size; i++){
            newList.add(list.get(i));
        }

        return newList;
    }
}
