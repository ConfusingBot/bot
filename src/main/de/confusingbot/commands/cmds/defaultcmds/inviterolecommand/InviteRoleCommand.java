package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.*;

import java.util.ArrayList;
import java.util.List;

public class InviteRoleCommand implements ServerCommand
{

    public InviteRoleCommand()
    {
        InviteRoleManager.embeds.HelpEmbed();
    }

    Embeds embeds = InviteRoleManager.embeds;
    SQL sql = InviteRoleManager.sql;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //invite add [@role] [Invitions]
        //invite remove [@role]

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (args.length >= 2)
        {
            switch (args[1])
            {
                case "leaderboard":
                    LeaderBoardCommand(channel);
                    break;

                case "info":
                    InfoCommand(channel);
                    break;

                case "stats":
                    StatsCommand(channel, member);
                    break;

                case "add":
                    AddCommand(channel, member, args, message);
                    break;

                case "remove":
                    RemoveCommand(channel, member, args, message);
                    break;

                case "list":
                    ListCommand(channel);
                    break;

                default:
                    //Usage
                    embeds.GeneralUsage(channel);
                    break;
            }
        }
        else
        {
            //Usage
            embeds.GeneralUsage(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void LeaderBoardCommand(TextChannel channel)
    {
        channel.getGuild().retrieveInvites().queue(inv -> {

            List<Invite> invites = new ArrayList<>();
            for (int i = 0; i < inv.size(); i++)
            {
                invites.add(inv.get(i));
            }

            //SortInvites
            Invite tempInvite;
            for (int i = 1; i < invites.size(); i++)
            {
                for (int j = 0; j < invites.size() - i; j++)
                {
                    if (invites.get(j).getUses() < invites.get(j + 1).getUses())
                    {
                        tempInvite = invites.get(j);
                        invites.set(j, invites.get(j + 1));
                        invites.set(j + 1, tempInvite);
                    }
                }
            }

            //Build Embed
            StringBuilder builder = new StringBuilder();
            int pos = 1;
            for (Invite invite : invites)
            {
                //maxAge == 0 infinity invite!
                if (invite.getMaxAge() != 0) continue;

                builder.append("**" + pos + "**   " + invite.getInviter().getAsMention() + " | " + invite.getUses() + "\n");
                pos++;
            }

            //Send Embed
            embeds.SendInviteLeaderBoard(channel, builder.toString());
        });
    }

    private void InfoCommand(TextChannel channel)
    {
        Guild guild = channel.getGuild();
        String list = getInvitionsList(guild);

        //Embed
        embeds.SendInfo(channel, list);
    }

    private void ListCommand(TextChannel channel)
    {
        Guild guild = channel.getGuild();
        String list = getInvitionsList(guild);

        //Message
        embeds.SendListEmbed(channel, list);
    }

    private void StatsCommand(TextChannel channel, Member member)
    {
        Guild guild = channel.getGuild();
        channel.getGuild().retrieveInvites().queue(invites -> {
            boolean hasAInvitionLink = false;
            for (Invite invite : invites)
            {
                if (invite.getInviter() == member.getUser())
                {
                    hasAInvitionLink = true;

                    //Message
                    embeds.SendUserInviteStats(channel, invite.getUses(), invite.getTimeCreated().toLocalDateTime(), member, invite.getUrl(), invite.getChannel());
                }
            }

            if (!hasAInvitionLink)
            {
                String list = getInvitionsList(guild);
                //Message
                embeds.SendInfo(channel, list);
            }
        });
    }

    private void AddCommand(TextChannel channel, Member member, String[] args, Message message)
    {
        if (member.hasPermission(channel, InviteRoleManager.createInviteRolePermission))
        {
            if (args.length >= 4)
            {
                Guild guild = channel.getGuild();
                List<Role> mentionedRoles = message.getMentionedRoles();
                if (mentionedRoles.size() >= 1)
                {
                    Role role = mentionedRoles.get(0);
                    String numberString = args[args.length - 1];

                    if (CommandsUtil.isNumeric(numberString))
                    {
                        int invitions = Integer.parseInt(args[args.length - 1]);

                        //SQL
                        sql.AddRoleToSQL(guild.getIdLong(), role.getIdLong(), invitions);

                        //Message
                        embeds.SuccessfullyAddedInviteRole(channel, role);
                    }
                    else
                    {
                        //Error
                        embeds.NoNumberError(channel, numberString);
                    }
                }
                else
                {
                    //Error
                    embeds.NoMentionedRoleError(channel);
                }
            }
            else
            {
                //Error
                embeds.InviteAddUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel, InviteRoleManager.createInviteRolePermission);
        }
    }

    private void RemoveCommand(TextChannel channel, Member member, String[] args, Message message)
    {
        if (member.hasPermission(channel, InviteRoleManager.createInviteRolePermission))
        {
            if (args.length >= 3)
            {
                Guild guild = channel.getGuild();
                List<Role> mentionedRoles = message.getMentionedRoles();
                if (mentionedRoles.size() >= 1)
                {
                    Role role = mentionedRoles.get(0);

                    //SQL
                    sql.RemoveRoleFromSQL(guild.getIdLong(), role.getIdLong());

                    //Message
                    embeds.SuccessfullyRemovedInviteRole(channel, role);
                }
                else
                {
                    //Error
                    embeds.NoMentionedRoleError(channel);
                }
            }
            else
            {
                //Error
                embeds.InviteRemoveUsage(channel);
            }
        }
        else
        {
            //Error
            embeds.NoPermissionError(channel, InviteRoleManager.createInviteRolePermission);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private String getInvitionsList(Guild guild)
    {
        StringBuilder builder = new StringBuilder();
        List<Long> roleIds = sql.getRoleIdsFromSQL(guild.getIdLong());
        List<Integer> invitions = sql.getInvitionsFromSQL(guild.getIdLong());

        for (int i = 0; i < roleIds.size(); i++)
        {
            Role role = guild.getRoleById(roleIds.get(i));
            if (role != null)
            {
                builder.append("\uD83D\uDDDD" + role.getAsMention() + " needs **" + invitions.get(i) + " invites**!\n");
            }
        }
        return builder.toString();
    }

}
