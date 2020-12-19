package de.confusingbot.commands.cmds.admincmds.reactrolescommand;

import de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListReactRolesRunnable implements Runnable
{
    Guild guild;
    TextChannel channel;
    long messageid;

    public ListReactRolesRunnable(Guild guild, TextChannel channel, long messageid){
        this.guild = guild;
        this.channel = channel;
        this.messageid = messageid;
    }

    @Override
    public void run(){
        List<Long> reactroleIds = new ArrayList<>();
        List<Long> messageIDs = new ArrayList<>();
        List<Long> channelIDs = new ArrayList<>();
        List<String> emoteStrings = new ArrayList<>();

        try
        {
            ResultSet set = ReactRoleManager.sql.GetReactRolesResultSet(guild.getIdLong());

            while (set.next())
            {
                reactroleIds.add(set.getLong("roleid"));
                messageIDs.add(set.getLong("messageid"));
                channelIDs.add(set.getLong("channelid"));
                emoteStrings.add(set.getString("emote"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (!reactroleIds.isEmpty())
        {
            //Create Description -> all voice channel
            String description = "";
            for (int i = 0; i < reactroleIds.size(); i++)
            {
                Role role = guild.getRoleById(reactroleIds.get(i));
                TextChannel roleAddChannel = guild.getTextChannelById(channelIDs.get(i));
                String emoteString = emoteStrings.get(i);
                long messageID = messageIDs.get(i);
                if (role != null && roleAddChannel != null && CommandsUtil.getLatestMessageIds(roleAddChannel).contains(messageID))
                {
                    if (CommandsUtil.isNumeric(emoteString))
                        emoteString = guild.getEmoteById(emoteString).getAsMention();

                    description += "" + emoteString + "   " + role.getAsMention() + "   in" + roleAddChannel.getAsMention() + "\n\n";
                }
                else
                {
                    description += "⚠️**ReactRole does not exists!**\n";
                    //SQL
                    ReactRoleManager.sql.removeFromSQL(guild.getIdLong(), channelIDs.get(i), messageID, emoteString, reactroleIds.get(i));
                }
            }

            //Delete Wait Message
            channel.deleteMessageById(messageid).queue();

            //Message
            ReactRoleManager.embeds.SendListEmbed(channel, description);
        }
        else
        {
            ReactRoleManager.embeds.HasNoReactRoleInformation(channel);
        }
    }
}
