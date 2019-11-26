package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;


public class AcceptRuleCommand implements ServerCommand
{

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //   args[0]   args[1]     args[2]     args[3]   args[4]     args[5]                    args[6]
        //- acceptrule add        [#channel] [messageID] [emote] [role rules not accepted] [role rules accepted]

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(channel, Permission.ADMINISTRATOR))
        {
            if (args.length >= 2)
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
                        AcceptRuleManager.embeds.GeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                AcceptRuleManager.embeds.GeneralUsage(channel);
            }
        }
        else
        {
            //Error
            AcceptRuleManager.embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (!AcceptRuleManager.sql.ExistsInSQL(message.getGuild().getIdLong()))
        {
            if (args.length == 7)
            {
                List<TextChannel> channels = message.getMentionedChannels();//args 1
                List<Role> roles = message.getMentionedRoles();//args 4 and 5

                if (!channels.isEmpty() && !roles.isEmpty() && roles.size() == 2)
                {
                    TextChannel textChannel = channels.get(0);//args 2
                    String messageIDString = args[3];//args 3
                    String emoteString = getEmote(message, args);//args 4
                    Role notAcceptedRole = roles.get(0);//args 5
                    Role acceptedRole = roles.get(1);//args 6

                    try
                    {
                        long messageID = Long.parseLong(messageIDString);

                        CommandsUtil.reactEmote(emoteString, textChannel, messageID, true);

                        //SQL
                        AcceptRuleManager.sql.addToSQL(channel.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, notAcceptedRole.getIdLong(), acceptedRole.getIdLong());

                        //Message
                        AcceptRuleManager.embeds.SuccessfulAddedAcceptRule(channel);

                    } catch (NumberFormatException e)
                    {
                        //Error
                        AcceptRuleManager.embeds.ThisIsNoIDError(channel, messageIDString);
                    }
                }
                else
                {
                    //Usage
                    AcceptRuleManager.embeds.AddUsage(channel);
                }
            }
            else
            {
                //Usage
                AcceptRuleManager.embeds.AddUsage(channel);
            }
        }
        else
        {
            //Error
            AcceptRuleManager.embeds.OnlyOneAcceptRuleAllowedError(channel);
        }
    }

    private void removeCommand(Guild guild, String[] args, TextChannel channel)
    {
        if (args.length == 2)
        {
            if (AcceptRuleManager.sql.ExistsInSQL(guild.getIdLong()))
            {
                //SQL
                AcceptRuleManager.sql.removeFormSQL(guild.getIdLong());

                //Message
                AcceptRuleManager.embeds.SuccessfulRemovedAcceptRule(channel);
            }
            else
            {
                //Error
                AcceptRuleManager.embeds.NoExistingAcceptRuleError(channel);
            }
        }
        else
        {
            //Usage
            AcceptRuleManager.embeds.RemoveUsage(channel);
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

}


