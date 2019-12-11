package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UpdateVotes
{
    //Called all 5Min
    public void onSecond()
    {
        try
        {
            ResultSet set = LiteSQL.onQuery("SELECT * FROM votecommand");
            while (set.next())
            {
                long id = set.getLong("id");
                long guildid = set.getLong("guildid");
                long channelid = set.getLong("channelid");
                long messageid = set.getLong("messageid");
                int endTime = set.getInt("endTime");
                String creationTime = set.getString("creationtime");
                String emotesString = set.getString("emotes");
                String title = set.getString("title");
                List<String> selectEmotes = Arrays.asList(emotesString.split(" "));

                long timeleft = getTimeLeft(creationTime, endTime);

                if (timeleft <= 0)
                {

                    //SQL
                    VoteCommandManager.sql.removeFromSQL(guildid, id);

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildid);
                    if (guild != null)
                    {
                        TextChannel channel = guild.getTextChannelById(channelid);
                        if (channel != null)
                        {
                            Message message = getMessage(channel, messageid);
                            if (CommandsUtil.getLatestMessages(channel).contains(messageid))
                            {
                                //Show that the vote has ended
                                CommandsUtil.reactEmote("❌", channel, messageid, true);

                                //Get Votes
                                List<Integer> votes = new ArrayList<>();
                                List<String> emotes = new ArrayList<>();
                                if (message != null)
                                {
                                    List<MessageReaction> messageReactions = message.getReactions();
                                    for (MessageReaction reaction : messageReactions)
                                    {
                                        String emote = reaction.getReactionEmote().getEmoji();
                                        int vote = reaction.getCount() - 1;
                                        if (!emotes.contains(emote) && selectEmotes.contains(emote))
                                        {
                                            emotes.add(emote);
                                            votes.add(vote);
                                        }
                                    }
                                }

                                //Sort Vote and Emotes
                                int voteTemp;
                                String emoteTemp;
                                for (int i = 1; i < votes.size(); i++)
                                {
                                    for (int j = 0; j < votes.size() - i; j++)
                                    {
                                        if (votes.get(j) < votes.get(j + 1))
                                        {
                                            //Votes
                                            voteTemp = votes.get(j);
                                            votes.set(j, votes.get(j + 1));
                                            votes.set(j + 1, voteTemp);

                                            //Emotes
                                            emoteTemp = emotes.get(j);
                                            emotes.set(j, emotes.get(j + 1));
                                            emotes.set(j + 1, emoteTemp);
                                        }
                                    }
                                }

                                //BuildMessage
                                StringBuilder builder = new StringBuilder();
                                for (int i = 0; i < votes.size(); i++)
                                {
                                    builder.append(emotes.get(i) + " | " + votes.get(i) + "\n\n");
                                }

                                //Message
                                VoteCommandManager.embeds.SendResultEmbed(channel, title, builder.toString().trim());
                            }
                            else
                            {
                                channel.sendMessage("Couldn't Send result!").queue();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Calculate TimeLeft
    private long getTimeLeft(String creationTimeString, int endTime)
    {
        long timeLeft = -1;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //get CreationTime
        LocalDateTime creationTime = LocalDateTime.parse(creationTimeString, formatter);

        //Get currentTime
        String currentTimeString = OffsetDateTime.now().toLocalDateTime().format(formatter);
        LocalDateTime currentTime = LocalDateTime.parse(currentTimeString, formatter);

        //Calculate timeleft
        Duration duration = Duration.between(creationTime, currentTime);
        long differentInHours = (duration.toMinutes());//if in toMinutes - 60 and not - 1!
        //System.out.println("Different in minutes " + differentInHours);
        timeLeft = endTime - differentInHours;

        return timeLeft;
    }

    private Message getMessage(TextChannel channel, long messageid)
    {
        Message message = null;

        for (Message m : channel.getIterableHistory().cache(false))
        {
            if (m.getIdLong() == messageid)
            {
                message = m;
            }
        }
        return message;
    }
}
