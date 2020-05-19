package main.de.confusingbot.commands.cmds.admincmds.votecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.sql.LiteSQL;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class UpdateVotes
{
    //Needed Permissions
    Permission MESSAGE_ADD_REACTION = Permission.MESSAGE_ADD_REACTION;
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

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
                String endTime = set.getString("endtime");
                String creationTime = set.getString("creationtime");
                String emotesString = set.getString("emotes");
                String title = set.getString("title");
                List<String> selectEmotes = CommandsUtil.encodeString(emotesString, ", ");

                //CurrentTime
                String currentTime = OffsetDateTime.now().toLocalDateTime().format(CommandsUtil.formatter);
                long timeLeftInMinutes = CommandsUtil.getTimeBetweenTwoDates(currentTime, endTime, false);

                if (timeLeftInMinutes <= 0)
                {
                    //SQL
                    VoteCommandManager.sql.removeFromSQL(guildid, id);

                    Guild guild = Main.INSTANCE.shardManager.getGuildById(guildid);
                    if (guild != null)
                    {
                        Member bot = guild.getSelfMember();
                        TextChannel channel = guild.getTextChannelById(channelid);
                        if (channel != null)
                        {
                            if (bot.hasPermission(channel, MESSAGE_ADD_REACTION))
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
                                            Emote emote = null;

                                            try
                                            {
                                                emoteString = reaction.getReactionEmote().getEmoji();
                                            } catch (IllegalStateException e)
                                            {
                                                emote = reaction.getReactionEmote().getEmote();
                                            }

                                            int vote = reaction.getCount() - 1;
                                            if (!emotes.contains(emoteString))
                                            {
                                                if (emote == null)
                                                {
                                                    if (selectEmotes.contains(emoteString))
                                                    {
                                                        emotes.add(emoteString);
                                                        votes.add(vote);
                                                    }
                                                }
                                                else
                                                {
                                                    if (selectEmotes.contains("" + emote.getIdLong()))
                                                    {
                                                        emotes.add(emote.getAsMention());
                                                        votes.add(vote);
                                                    }
                                                }
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
                                    //Message
                                    EmbedManager.SendMessage("⚠️ This Vote has been deleted!", channel, 0);
                                }
                            }
                            else
                            {
                                //Error
                                EmbedManager.SendNoPermissionEmbed(channel, MESSAGE_ADD_REACTION, "VoteCommand | Can't react with the 'x' emote!");
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
