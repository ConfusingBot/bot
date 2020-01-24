package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import com.vdurmont.emoji.EmojiManager;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VoteCommand implements ServerCommand
{

    public VoteCommand()
    {
        VoteCommandManager.embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //vote create [#channel] [time in hours] [Header] -1- text1 -2- text2 -3- text3 -emote- text4 ([specialRoles])
        //vote remove messageid

        Guild guild = channel.getGuild();
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (member.hasPermission(channel, VoteCommandManager.permission))
        {

            if (args.length >= 2)
            {
                switch (args[1])
                {
                    case "create":
                        CreateCommand(args, guild, channel, message);
                        break;
                    case "remove":
                        RemoveCommand(args, guild, channel, message);
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
            VoteCommandManager.embeds.NoPermissionError(channel, VoteCommandManager.permission);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void CreateCommand(String[] args, Guild guild, TextChannel channel, Message message)
    {
        if (args.length > 5)
        {
            String channelString = args[2];
            List<TextChannel> mentionedChannels = message.getMentionedChannels();
            List<Role> mentionedRoles = message.getMentionedRoles();
            List<Emote> mentionedEmotes = message.getEmotes();

            if (mentionedChannels.size() > 0)
            {
                if (channelString.contains(mentionedChannels.get(0).getName()))
                {
                    TextChannel textChannel = mentionedChannels.get(0);
                    if (CommandsUtil.isNumeric(args[3]))
                    {
                        long timeInHours = Long.parseLong(args[3]);
                        List<String> wholeStringWordList;
                        String title = "";
                        List<String> emojiStrings = new ArrayList<>();
                        List<String> text = new ArrayList<>();
                        List<String> allowedRoleIDs = new ArrayList<>();
                        String wholeString = "";
                        for (int i = 4; i < args.length; i++)
                        {
                            wholeString += args[i] + " ";
                        }
                        wholeString = wholeString.trim();

                        //=============================================================================================
                        //Get Roles
                        //=============================================================================================
                        wholeStringWordList = new ArrayList<>(Arrays.asList(wholeString.split("@")));
                        for (int i = 0; i < wholeStringWordList.size(); i++)
                        {
                            String word = wholeStringWordList.get(i);
                            boolean removed = false;
                            for (Role role : mentionedRoles)
                            {
                                String roleString = role.getName();
                                word = word.trim();
                                if (word.equals(roleString))
                                {
                                    allowedRoleIDs.add("" + role.getIdLong());
                                    wholeStringWordList.set(i, "");
                                    removed = true;
                                }
                            }

                            //because of the split it lose the @ also for the roles mentioned for example in the header
                            if (!removed && i != 0)
                            {
                                wholeStringWordList.set(i, "@" + word);
                            }
                        }
                        String newString = String.join(" ", wholeStringWordList);
                        //=============================================================================================
                        //Get Roles
                        //=============================================================================================

                        //=============================================================================================
                        //Get Title
                        //=============================================================================================
                        title = newString.substring(0, newString.indexOf(VoteCommandManager.voteEmotePrefix));
                        newString = newString.replace(title, "");
                        if (title.isEmpty()) title = "Vote!";
                        //=============================================================================================
                        //Get Title
                        //=============================================================================================

                        //=============================================================================================
                        //Get Emotes and Texts
                        //=============================================================================================
                        wholeStringWordList = new ArrayList<>(Arrays.asList(newString.split(" ")));

                        String sentence = "";
                        for (String word : wholeStringWordList)
                        {
                            if (word.startsWith(VoteCommandManager.voteEmotePrefix) && word.endsWith(VoteCommandManager.voteEmotePrefix))
                            {
                                word = word.replace(VoteCommandManager.voteEmotePrefix, "");
                                boolean added = false;

                                for (Emote emote : mentionedEmotes)
                                {
                                    if (word.equals(":" + emote.getName() + ":"))
                                    {
                                        emojiStrings.add(emote.getIdLong() + "");
                                        added = true;
                                    }

                                }

                                if (!added)
                                    emojiStrings.add(word);

                                word = "";

                                if (sentence != "")
                                {
                                    sentence.trim();
                                    text.add(sentence);
                                    sentence = "";
                                }
                            }
                            sentence += word + " ";
                        }
                        text.add(sentence);

                        //Trim the Votes to a specific size
                        if (emojiStrings.size() > VoteCommandManager.voteEmotes.size())
                        {
                            for (int i = (VoteCommandManager.voteEmotes.size() - 1); i < emojiStrings.size(); i++)
                            {
                                emojiStrings.remove(i);
                                text.remove(i);
                            }

                            //Message
                            VoteCommandManager.embeds.ToManyVotesInformation(channel, VoteCommandManager.voteEmotes.size());
                        }
                        //=============================================================================================
                        //Get Emotes and Texts
                        //=============================================================================================

                        //Check if only one item for voting exists
                        if (emojiStrings.size() < 2)
                        {
                            VoteCommandManager.embeds.OnlyOneVoteTopic(channel);
                            return;
                        }

                        //Build vote Text
                        String voteText = buildVoteText(text, emojiStrings, guild, allowedRoleIDs);

                        //Message
                        long messageid = VoteCommandManager.embeds.SendVoteEmbed(textChannel, title, voteText, timeInHours);

                        //Add emotes to message
                        if (!addEmotesToMessage(emojiStrings, messageid, textChannel)) return;

                        //Get CurrentTime
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String creationTime = OffsetDateTime.now().toLocalDateTime().format(formatter);

                        //Get RemoveTime
                        String removeTime = CommandsUtil.AddXTime(OffsetDateTime.now().toLocalDateTime(), timeInHours, true).format(CommandsUtil.formatter);

                        //SQL
                        VoteCommandManager.sql.addToSQL(
                                guild.getIdLong(),
                                textChannel.getIdLong(),
                                messageid,
                                title,
                                CommandsUtil.codeString(allowedRoleIDs, ", "),
                                CommandsUtil.codeString(emojiStrings, ", "),
                                removeTime,
                                creationTime
                        );
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

    private void RemoveCommand(String[] args, Guild guild, TextChannel channel, Message message)
    {
        channel.sendMessage("```You can simply delete the VoteMessage for now```").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }

    //=====================================================================================================================================
    //Help
    //=====================================================================================================================================
    private String buildVoteText(List<String> texts, List<String> emotes, Guild guild, List<String> allowedRoleIDs)
    {
        String voteText = "";
        StringBuilder builder = new StringBuilder();

        //Add
        for (int i = 0; i < emotes.size(); i++)
        {
            String emoteString = emotes.get(i);
            String text = texts.get(i);

            if (CommandsUtil.isNumeric(emoteString))
            {
                //React with emote ID
                long emoteID = Long.parseLong(emoteString);
                Emote emote = guild.getEmoteById(emoteID);
                if (emote != null)
                {
                    emoteString = emote.getAsMention();
                }
                else
                {
                    emoteString = VoteCommandManager.voteEmotes.get(i);
                }
            }
            else
            {
                if (!EmojiManager.isEmoji(emoteString))
                {
                    emoteString = VoteCommandManager.voteEmotes.get(i) + "(**" + emoteString + "**)";
                }
            }

            builder.append(emoteString + "   " + text + "\n\n");
        }

        //Add the allowed roles
        builder.append("\n**Allowed Roles:** ");
        if (!allowedRoleIDs.isEmpty())
        {
            for (String roleId : allowedRoleIDs)
            {
                long id = Long.parseLong(roleId);
                Role role = guild.getRoleById(id);

                builder.append(role.getAsMention() + " ");
            }

        }
        else
        {
            builder.append("@everyone");
        }

        voteText = builder.toString().trim();

        return voteText;
    }


    private boolean addEmotesToMessage(List<String> emotes, long messageid, TextChannel channel)
    {
        for (int i = 0; i < emotes.size(); i++)
        {
            String emote = emotes.get(i);
            if (!CommandsUtil.reactEmote(emote, channel, messageid, true))
            {
                if (VoteCommandManager.voteEmotes.size() > i)
                {
                    //if it is not 1, 2, 3, 4, 5, 6, 7, 8, 9
                    if (!CommandsUtil.isNumeric(emote) && emote.length() != 1)
                    {
                        VoteCommandManager.embeds.NoEmoteError(channel, emote);
                    }

                    //add defaut Vote Emote at the position i
                    String numberEmote = VoteCommandManager.voteEmotes.get(i);
                    CommandsUtil.reactEmote(numberEmote, channel, messageid, true);
                    emotes.set(i, numberEmote);
                }
                else
                {
                    VoteCommandManager.embeds.NoEmoteError(channel, emote);
                    channel.deleteMessageById(messageid);

                    return false;
                }
            }

        }
        return true;
    }
}
