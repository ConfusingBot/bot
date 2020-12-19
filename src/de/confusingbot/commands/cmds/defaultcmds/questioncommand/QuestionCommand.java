package de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import de.confusingbot.commands.help.CommandsUtil;
import de.confusingbot.commands.types.ServerCommand;
import de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public class QuestionCommand implements ServerCommand
{
    public QuestionCommand()
    {
        QuestionManager.embeds.HelpEmbed();
    }

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;
    Permission MANAGE_CHANNEL = Permission.MANAGE_CHANNEL;
    Permission MESSAGE_MANAGE = Permission.MESSAGE_MANAGE;

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        //- question ([Title]) QUESTION: [Question] [Type(Role of the server for Example @Programming)]
        //- question close
        //-question category create ID
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length >= 2)
            {
                //other Commands
                Guild guild = channel.getGuild();
                switch (args[1])
                {
                    case "close":
                        CloseQuestionCommand(args, guild, member, channel);
                        break;
                    case "info":
                        InfoQuestionCommand(args, guild, member, channel);
                        break;
                    case "category":
                        CategoryHandler(message, channel, args);
                        break;
                    default:
                        int minWords = 3;
                        if (args.length >= 1 + minWords)
                        {
                            //Create Question
                            CreateQuestionCommand(channel.getGuild(), member, channel, message, args);
                        }
                        else
                        {
                            //Usage
                            QuestionManager.embeds.MinXWords(channel, minWords);
                        }
                        break;
                }
            }
            else
            {
                //Usage
                QuestionManager.embeds.GeneralUsage(channel);
            }
        }
    }

    private void CategoryHandler(Message message, TextChannel channel, String[] args)
    {
        Member member = message.getMember();
        Guild guild = channel.getGuild();
        if (member.hasPermission(channel, QuestionManager.questionCategoryPermission))
        {
            if (args.length == 3 || args.length == 4)
            {
                switch (args[2])
                {
                    case "create":
                        CategoryCreateCommand(args, guild, channel);//for creating a category
                        break;
                    case "remove":
                        CategoryRemoveCommand(args, guild, channel);//for creating a category
                        break;
                    default:
                        //Usage
                        QuestionManager.embeds.QuestionCategoryGeneralUsage(channel);
                        break;
                }
            }
            else
            {
                //Usage
                QuestionManager.embeds.QuestionCategoryGeneralUsage(channel);
            }
        }
        else
        {
            //Error
            QuestionManager.embeds.NoPermissionError(channel, QuestionManager.questionCategoryPermission);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void CategoryCreateCommand(String[] args, Guild guild, TextChannel channel)
    {
        if (args.length == 4)
        {
            if (CommandsUtil.isNumeric(args[3]))
            {
                long categoryid = Long.parseLong(args[3]);
                if (!QuestionManager.sql.ServerHasQuestionCategory(guild.getIdLong()))
                {
                    Category category = guild.getCategoryById(categoryid);

                    if (category != null)
                    {
                        //SQL
                        QuestionManager.sql.AddQuestionCategorieToSQL(guild.getIdLong(), categoryid);

                        //Message
                        QuestionManager.embeds.SuccessfullyAddedCategoryToQuestionCategory(channel, category.getName());
                    }
                    else
                    {
                        //Error
                        QuestionManager.embeds.ThisIsNoIDError(channel, args[3]);
                    }
                }
                else
                {
                    //Error
                    QuestionManager.embeds.OnlyOneAllowedQuestionCategory(channel);
                }
            }
            else
            {
                //Error
                QuestionManager.embeds.ThisIsNoIDError(channel, args[3]);
            }
        }
        else
        {
            //Usage
            QuestionManager.embeds.QuestionCategoryCreateUsage(channel);
        }
    }

    private void CategoryRemoveCommand(String[] args, Guild guild, TextChannel channel)
    {
        if (args.length == 3)
        {
            if (QuestionManager.sql.ServerHasQuestionCategory(guild.getIdLong()))
            {
                Category category = QuestionManager.sql.GetQuestionCategory(guild);

                //SQL
                QuestionManager.sql.RemoveQuestionCategoryFromSQL(guild.getIdLong());

                //Message
                QuestionManager.embeds.SuccessfullyRemovedCategoryToQuestionCategory(channel, category != null ? category.getName() : "unknown Category");
            }
            else
            {
                //Error
                QuestionManager.embeds.ServerHasNoQuestionCategoryError(channel);
            }
        }
        else
        {
            //Usage
            QuestionManager.embeds.QuestionCategoryRemoveUsage(channel);
        }
    }

    private void CloseQuestionCommand(String[] args, Guild guild, Member member, TextChannel channel)
    {
        if (args.length == 2)
        {
            //SQL
            Member sentQuestionMember = QuestionManager.sql.GetQuestionAskMember(guild, channel.getIdLong());

            if (sentQuestionMember != null)
            {
                if (bot.hasPermission(channel, MANAGE_CHANNEL))
                {
                    long memberid = sentQuestionMember.getIdLong();

                    if (member == sentQuestionMember || member.hasPermission(channel, Permission.ADMINISTRATOR))
                    {
                        int deletedInSeconds = 5;
                        //Message
                        QuestionManager.embeds.QuestionChannelWillBeDeletedInXSeconds(channel, deletedInSeconds);

                        Runnable r = new DeleteQuestionRunnable(guild, channel, memberid, deletedInSeconds, QuestionManager.sql);
                        new Thread(r).start();
                    }
                    else
                    {
                        //Error
                        QuestionManager.embeds.NoPermissionForClosingThisQuestionChannelError(channel);
                    }
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(channel, MANAGE_CHANNEL, "");
                }
            }
            else
            {
                //Error
                QuestionManager.embeds.YouAreNotInAQuestionChannelError(channel);
            }
        }
        else
        {
            //Usage
            QuestionManager.embeds.QuestionCloseUsage(channel);
        }
    }

    private void InfoQuestionCommand(String[] args, Guild guild, Member member, TextChannel channel)
    {
        if (args.length == 2)
        {
            //SQL
            Member sentQuestionMember = QuestionManager.sql.GetQuestionAskMember(guild, channel.getIdLong());

            if (sentQuestionMember != null)
            {
                String deleteTime = QuestionManager.sql.getQuestionDeleteTime(guild.getIdLong(), channel.getIdLong());
                String creationTime = QuestionManager.sql.getQuestionCreationTime(guild.getIdLong(), channel.getIdLong());
                long timeleft = CommandsUtil.getTimeBetweenTwoDates(OffsetDateTime.now().toLocalDateTime().format(QuestionManager.formatter), deleteTime, true);
                String questionTypeName = channel.getName().equals(QuestionManager.DefaultQuestionName) ? "none" : channel.getName();

                //Embed
                QuestionManager.embeds.SendQuestionInfo(channel, member, sentQuestionMember, timeleft, LocalDateTime.parse(creationTime, QuestionManager.formatter), questionTypeName);
            }
            else
            {
                //Error
                QuestionManager.embeds.YouAreNotInAQuestionChannelError(channel);
            }
        }
        else
        {
            //Usage
            QuestionManager.embeds.QuestionInfoUsage(channel);
        }
    }

    private void CreateQuestionCommand(Guild guild, Member member, TextChannel channel, Message message, String[] args)
    {

        if (QuestionManager.sql.ServerHasQuestionCategory(guild.getIdLong()))
        {
            Category category = QuestionManager.sql.GetQuestionCategory(guild);
            if (category != null)
            {
                if (bot.hasPermission(MANAGE_CHANNEL))
                {
                    if (bot.hasPermission(channel, MESSAGE_MANAGE))
                    {
                        List<Role> roles = message.getMentionedRoles();
                        int mentionableRoles = 3;
                        if (roles != null && roles.size() <= mentionableRoles)
                        {
                            TextChannel textChannel;
                            if (roles.size() > 0)
                            {
                                //If member mentioned a role, the TextChannel will be named after the first mentioned role!
                                textChannel = category.createTextChannel("❓" + roles.get(0).getName()).complete();
                            }
                            else
                            {
                                //If not the TextChannel will be named Question
                                textChannel = category.createTextChannel(QuestionManager.DefaultQuestionName).complete();
                            }

                            String wholeQuestion = buildQuestionString(args, roles, 1);
                            String questionTitle = "";
                            String question = "";
                            String roleString = createRoleString(roles);

                            if (wholeQuestion.contains(QuestionManager.questionKey))
                            {
                                String[] questionParts = wholeQuestion.split(QuestionManager.questionKey);
                                questionTitle = "**" + questionParts[0] + "**";
                                question = questionParts[1];
                            }
                            else
                            {
                                question = wholeQuestion;
                            }

                            //Send Question Message
                            EmbedBuilder builder = QuestionManager.embeds.CreateQuestionEmbed(member, questionTitle, question, roleString);
                            textChannel.sendMessage(builder.build()).queue(mes -> {
                                textChannel.pinMessageById(mes.getIdLong()).queue();
                            });

                            //SQLData
                            long channelID = textChannel.getIdLong();
                            long guildID = textChannel.getGuild().getIdLong();
                            long memberID = member.getIdLong();

                            String creationTime = OffsetDateTime.now().toLocalDateTime().format(QuestionManager.formatter);
                            String deleteTime = CommandsUtil.AddXTime(OffsetDateTime.now().toLocalDateTime(), QuestionManager.deleteMessageAfterXHours, QuestionManager.hours).format(QuestionManager.formatter);

                            //SQL
                            QuestionManager.sql.AddGeneratedQuestionToSQL(guildID, channelID, memberID, creationTime, deleteTime);
                        }
                        else
                        {
                            QuestionManager.embeds.YouCanOnlyMentionOneRoleInAQuestionError(channel, mentionableRoles);
                        }
                    }
                    else
                    {
                        //Error
                        EmbedManager.SendNoPermissionEmbed(channel, MESSAGE_MANAGE, "");
                    }
                }
                else
                {
                    //Error
                    EmbedManager.SendNoPermissionEmbed(channel, MANAGE_CHANNEL, "");
                }
            }
            else
            {
                //Error
                QuestionManager.embeds.QuestionCategoryDoesNotExistAnyMore(channel);

                //Remove Question Category
                QuestionManager.sql.RemoveQuestionCategoryFromSQL(guild.getIdLong());
            }
        }
        else
        {
            //Information
            QuestionManager.embeds.ThisServerHasNoExistingQuestionCategoryInformation(channel);
        }
    }

    //=====================================================================================================================================
    //Helper
    //=====================================================================================================================================
    private String buildQuestionString(String[] args, List<Role> roles, int startIndex)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < args.length; i++)
        {
            builder.append(args[i] + " ");
        }

        String question = builder.toString();
        question.trim();

        //delete roles from the message
        for (Role role : roles)
        {
            String roleName = "@" + role.getName();
            question = question.replace(roleName, "");
        }

        return question;
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

}
