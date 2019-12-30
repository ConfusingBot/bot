package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
                List<String> selectEmotes = CommandsUtil.encodeString(emotesString, ", ");

                //long timeleft = CommandsUtil.getTimeLeftInMinutes(creationTime, endTime);
                long timeleft = CommandsUtil.getTimeLeftInHours(creationTime, endTime);

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
                            Message message = CommandsUtil.getLatestesMessageByID(channel, messageid);
                            if (message != null)
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
                                        String emoteString = "";

                                        try
                                        {
                                            emoteString = reaction.getReactionEmote().getEmoji();
                                        }
                                        catch (IllegalStateException e)
                                        {
                                            emotesString = reaction.getReactionEmote().getEmote().getAsMention();
                                        }

                                        int vote = reaction.getCount() - 1;
                                        if (!emotes.contains(emoteString) && selectEmotes.contains(emoteString))
                                        {
                                            emotes.add(emoteString);
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
                                channel.sendMessage("This Vote has been deleted").queue();
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

}
