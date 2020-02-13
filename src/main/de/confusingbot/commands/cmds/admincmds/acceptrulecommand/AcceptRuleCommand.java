package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.util.List;


public class AcceptRuleCommand implements ServerCommand
{

    public AcceptRuleCommand()
    {
        AcceptRuleManager.embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //   args[0]   args[1]     args[2]     args[3]   args[4]     args[5]                    args[6]
        //- acceptrule add        [#channel] [messageID] [emote] [role rules accepted]  [role rules not accepted]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (member.hasPermission(channel, AcceptRuleManager.permission))
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
                    case "show":
                        showCommand(message.getGuild(), args, channel);
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
            AcceptRuleManager.embeds.NoPermissionError(channel, AcceptRuleManager.permission);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void addCommand(Message message, String[] args, TextChannel channel)
    {
        if (!AcceptRuleManager.sql.ExistsInSQL(message.getGuild().getIdLong()))
        {
            if (args.length >= 6)
            {
                List<TextChannel> channels = message.getMentionedChannels();//args 1
                List<Role> roles = message.getMentionedRoles();//args 4 and 5

                if (!channels.isEmpty() && !roles.isEmpty() && roles.size() >= 1)
                {
                    TextChannel textChannel = channels.get(0);//args 2
                    String messageIDString = args[3];//args 3
                    String emoteString = CommandsUtil.getEmote(message, args[4]);//args 4
                    Role acceptedRole = roles.get(0);//args 5
                    Role notAcceptedRole = roles.size() > 1 ? roles.get(1) : null;//args 6

                    if (CommandsUtil.isNumeric(messageIDString))
                    {
                        long messageID = Long.parseLong(messageIDString);

                        if (!CommandsUtil.reactEmote(emoteString, textChannel, messageID, true))
                        {
                            //Error
                            AcceptRuleManager.embeds.ThisIsNoIDError(channel, messageIDString);
                        }

                        //Add acceptedRole to all members
                        CommandsUtil.AddOrRemoveRoleFromAllMembers(textChannel.getGuild(), acceptedRole.getIdLong(), true);

                        //SQL
                        AcceptRuleManager.sql.addToSQL(channel.getGuild().getIdLong(), textChannel.getIdLong(), messageID, emoteString, notAcceptedRole != null ?notAcceptedRole.getIdLong() : -1, acceptedRole.getIdLong());

                        //Message
                        AcceptRuleManager.embeds.SuccessfulAddedAcceptRule(channel);
                    }
                    else
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
        long guildID = channel.getGuild().getIdLong();
        if (args.length == 2)
        {
            if (AcceptRuleManager.sql.ExistsInSQL(guildID))
            {
                long acceptedRoleID = AcceptRuleManager.sql.getAcceptedRoleID(guildID);
                long notAcceptedRoleID = AcceptRuleManager.sql.getNotAcceptedRoleID(guildID);

                //RemoveRoles
                CommandsUtil.AddOrRemoveRoleFromAllMembers(channel.getGuild(), acceptedRoleID, false);
                CommandsUtil.AddOrRemoveRoleFromAllMembers(channel.getGuild(), notAcceptedRoleID, false);

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

    private void showCommand(Guild guild, String[] args, TextChannel channel)
    {
        long guildID = guild.getIdLong();
        if (args.length == 2)
        {
            if (AcceptRuleManager.sql.ExistsInSQL(guildID))
            {

                long acceptedRoleID = AcceptRuleManager.sql.getAcceptedRoleID(guildID);
                long notAcceptedRoleID = AcceptRuleManager.sql.getNotAcceptedRoleID(guildID);
                long channelID = AcceptRuleManager.sql.getChannelID(guildID);
                String emote = AcceptRuleManager.sql.getEmote(guildID);

                Role acceptedRole = guild.getRoleById(acceptedRoleID);
                Role notAcceptedRole = guild.getRoleById(notAcceptedRoleID);
                TextChannel textChannel = guild.getTextChannelById(channelID);

                String acceptedRoleString = "Not Existing";
                String notAcceptedRoleString = "Not Existing";
                String textChannelString = "Not Existing";

                if (acceptedRole != null) acceptedRoleString = acceptedRole.getAsMention();
                if (notAcceptedRole != null) notAcceptedRoleString = notAcceptedRole.getAsMention();
                if (textChannel != null) textChannelString = textChannel.getAsMention();

                //Message
                AcceptRuleManager.embeds.ShowAcceptRule(channel, textChannelString, notAcceptedRoleString, acceptedRoleString, emote);

            }
            else
            {
                //Information
                AcceptRuleManager.embeds.ServerHasNoAcceptedRuleInformation(channel);
            }
        }
        else
        {
            //Usage
            AcceptRuleManager.embeds.ShowUsage(channel);
        }
    }
}


