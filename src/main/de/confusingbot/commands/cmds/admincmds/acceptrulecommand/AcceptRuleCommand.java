package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import org.jsoup.internal.StringUtil;

import java.util.List;


public class AcceptRuleCommand implements ServerCommand
{

    private SQL sql = new SQL();
    private Strings strings = new Strings();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //   args[0]   args[1]     args[2]     args[3]   args[4]     args[5]                    args[6]
        //- acceptrule add        [#channel] [messageID] [emote] [role rules not accepted] [role rules accepted]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length > 2)
            {
                switch (args[1])
                {
                    case "add":
                        addCommand(message, args, channel);
                        break;
                    case "remove":
                        removeCommand(message.getGuild(), args, channel);
                        break;
                    default:
                        //Usage
                        strings.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                strings.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
           strings.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (args.length == 7)
        {
            if (!sql.ExistsInSQL(message.getGuild().getIdLong()))
            {
                List<TextChannel> channels = message.getMentionedChannels();//args 1
                List<Role> roles = message.getMentionedRoles();//args 4 and 5

                if (!channels.isEmpty() && !roles.isEmpty() && roles.size() == 2)
                {
                    TextChannel textChannel = channels.get(0);//args 2
                    String messageIDString = args[2];//args 3
                    String emoteString = getEmote(message, args);//args 4
                    Role notAcceptedRole = roles.get(0);//args 5
                    Role acceptedRole = roles.get(1);//args 6

                    try
                    {
                        long messageID = Long.parseLong(messageIDString);

                        reactEmote(emoteString, textChannel, messageID, true);

                        //SQL
                        sql.addToSQL(channel.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, notAcceptedRole.getIdLong(), acceptedRole.getIdLong());

                        //Message
                        strings.SuccessfulAddedAcceptRule(channel);

                    } catch (NumberFormatException e)
                    {
                        //Error
                        strings.ThisIsNoIDError(channel, messageIDString);
                    }
                }
                else
                {
                    //Usage
                    strings.AddUsage(channel);
                }
            }
            else
            {
                //Error
                strings.OnlyOneAcceptRuleAllowedError(channel);
            }
        }
        else
        {
            //Usage
            strings.AddUsage(channel);
        }
    }

    private void removeCommand(Guild guild, String[] args, TextChannel channel)
    {
        if (args.length > 2)
        {
            if (sql.ExistsInSQL(guild.getIdLong()))
            {
                //SQL
                sql.removeFormSQL(guild.getIdLong());

                //Message
                strings.SuccessfulRemovedAcceptRule(channel);
            }
            else
            {
                //Error
               strings.NoExistingAcceptRuleError(channel);
            }
        }
        else
        {
            //Usage
            strings.RemoveUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private String getEmote(Message message, String[] args)
    {
        List<Emote> emotes = message.getEmotes();

        String emoteString = "";
        if (!emotes.isEmpty())
        {
            Emote emote = emotes.get(0);
            emoteString += emote.getIdLong();
        }
        else
        {
            String emote = args[4];
            emoteString += emote;
        }
        return emoteString;
    }

    private static void reactEmote(String emoteString, TextChannel channel, long messageid, boolean add)
    {
        if (CommandsUtil.isNumeric(emoteString))//if emoteString is a emoteID
        {
            Emote emote = channel.getGuild().getEmoteById(Long.parseLong(emoteString));
            if (add)
                channel.addReactionById(messageid, emote).queue();
            else
                channel.removeReactionById(messageid, emote).queue();
        }
        else
        {
            if (add)
                channel.addReactionById(messageid, emoteString).queue();
            else
                channel.removeReactionById(messageid, emoteString).queue();
        }
    }

}


