package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.awt.*;
import java.time.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuestionCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- question [Title] QUESTION: [Question] [Type(Role of the server for Example @Programming)]
        //- question close
        //-question category create/remove
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length > 3)
        {
            //Create Question
            CreateQuestionCommand(channel.getGuild(), member, channel, message, args);
        }
        else if (args.length >= 2)
        {
            //other Commands
            Guild guild = channel.getGuild();
            switch (args[1])
            {
                case "close":
                    CloseQuestionCommand(args, guild, member, channel);
                    break;
                case "category":
                    if (member.hasPermission(channel, Permission.ADMINISTRATOR))
                    {
                        switch (args[2])
                        {
                            case "create":
                                categoryCreateCommand(args, guild, channel, member);//for creating a category
                                break;
                            case "remove":
                                categoryRemoveCommand(args, guild, channel, member);//for creating a category
                                break;
                            default:
                                break;
                        }
                    }
                    else
                    {
                        //Error
                        embeds.NoPermissionError(channel);
                    }
                    break;

                default:
                    //Usage
                    embeds.QuestionCloseUsage(channel);
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
    private void categoryCreateCommand(String[] args, Guild guild, TextChannel channel, Member member)
    {
        if (args.length == 3)
        {
            try
            {
                long categoryid = Long.parseLong(args[2]);
                if (!sql.ServerHasQuestionCategory(guild.getIdLong()))
                {
                    //SQL
                    sql.AddQuestionCategorieToSQL(guild.getIdLong(), categoryid);

                    //Message
                    embeds.SuccessfullyAddedCategoryToQuestionCategory(channel, guild.getCategoryById(categoryid).getName());
                }
                else
                {
                    //Error
                    embeds.OnlyOneAllowedQuestionCategory(channel);
                }
            } catch (NumberFormatException e)
            {
                //Error
                embeds.ThisIsNoIDError(channel, args[2]);
            }
        }
        else
        {
            //Usage
            embeds.QuestionCategoryCreateUsage(channel);
        }
    }

    private void categoryRemoveCommand(String[] args, Guild guild, TextChannel channel, Member member)
    {
        if (args.length == 2)
        {
            if (sql.ServerHasQuestionCategory(guild.getIdLong()))
            {
                Category category = sql.GetQuestionCategory(guild);

                //SQL
                sql.RemoveQuestionCategoryFromSQL(guild.getIdLong());

                //Message
                embeds.SuccessfullyRemovedCategoryToQuestionCategory(channel, category.getName());
            }
            else
            {
                //Error
                embeds.ServerHasNoQuestionCategoryError(channel);
            }
        }
        else
        {
            //Usage
            embeds.QuestionCategoryRemoveUsage(channel);
        }
    }

    private void CloseQuestionCommand(String[] args, Guild guild, Member member, TextChannel channel)
    {
        if (args.length == 2)
        {
            //SQL
            Member sentQuestionMember = sql.GetQuestionAskMember(guild, channel.getIdLong());

            if (sentQuestionMember != null)
            {

                long memberid = sentQuestionMember.getIdLong();

                if (member == sentQuestionMember || member.hasPermission(channel, Permission.ADMINISTRATOR))
                {
                    int deletedInSeconds = 5;
                    //Message
                    embeds.QuestionChannelWillBeDeletedInXSeconds(channel, deletedInSeconds);

                    sleepXSeconds(deletedInSeconds);

                    //SQL
                    sql.RemoveQuestionChannelFromSQL(guild.getIdLong(), channel.getIdLong(), memberid);

                    //Delete Channel
                    channel.delete().queue();
                }
                else
                {
                    //Error
                    embeds.NoPermissionForClosingThisQuestionChannelError(channel);
                }
            }
            else
            {
                //Error
                embeds.YouAreNotInAQuestionChannelError(channel);
            }
        }
        else
        {
            //Usage
            embeds.QuestionCloseUsage(channel);
        }
    }

    private void CreateQuestionCommand(Guild guild, Member member, TextChannel channel, Message message, String[] args)
    {
        List<Role> roles = message.getMentionedRoles();

        int mentionableRoles = 3;
        if (roles.size() > mentionableRoles)
        {
            embeds.YouCanOnlyMentionOneRoleInAQuestionError(channel, mentionableRoles);
        }
        else
        {
            Category category = sql.GetQuestionCategory(guild);
            if (category != null)
            {
                TextChannel textChannel;
                if (roles != null && roles.size() > 0)
                {
                    //If member mentioned a role, the TextChannel will be named after the first mentioned role!
                    textChannel = category.createTextChannel("❓" + roles.get(0).getName()).complete();
                }
                else
                {
                    //If not the TextChannel will be named Question
                    textChannel = category.createTextChannel("❓Question").complete();
                }

                //Send Question Message
                EmbedBuilder builder = createQuestionEmbed(args, roles, member);
                EmbedManager.SendEmbed(builder, textChannel, 0);

                //SQL
                sql.AddGeneratedQuestionToSQL(member, textChannel);
            }
            else
            {
                //Error
                embeds.ThisServerHasNoExistingQuestionCategoryError(channel);
            }
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private EmbedBuilder createQuestionEmbed(String[] args, List<Role> roles, Member member)
    {
        String questionMarker = "QUESTION:";

        String m = removeRoles(args, roles);

        String questionTitle = m.substring(0, m.indexOf(questionMarker));
        String question = m.substring((m.indexOf(questionMarker) + questionMarker.length()));
        String roleString = createRoleString(roles);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a1f542"));
        builder.setDescription("**❓ Question** form " + member.getAsMention() + "\n\n\n");
        builder.addField("**" + questionTitle + "**\n\n", question, false);
        builder.addField("", roleString, false);
        builder.setTimestamp(OffsetDateTime.now());
        builder.setThumbnail(member.getUser().getEffectiveAvatarUrl());

        return builder;
    }

    private String removeRoles(String[] args, List<Role> roles)
    {
        String m = "";
        for (int i = 1; i < args.length
                ; i++)
        {
            m += (args[i] + " ");
        }

        for (Role role : roles)
        {
            String roleName = "@" + role.getName();
            m = m.replace(roleName, "");
        }

        m.trim();

        return m;
    }

    private String createRoleString(List<Role> roles)
    {
        String roleString = "";
        for (Role role : roles)
        {
            roleString += " " + role.getAsMention();
        }
        return roleString.trim();
    }

    private void sleepXSeconds(int seconds)
    {
        try
        {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
