package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuestionCommand implements ServerCommand
{

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

    String questionKey = "QUESTION:";

    public QuestionCommand(){
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- question ([Title]) QUESTION: [Question] [Type(Role of the server for Example @Programming)]
        //- question close
        //-question category create ID
        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (args.length >= 2)
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
                                    embeds.QuestionCategoryGeneralUsage(channel);
                                    break;
                            }
                        }
                        else
                        {
                            //Usage
                            embeds.QuestionCategoryGeneralUsage(channel);
                        }
                    }
                    else
                    {
                        //Error
                        embeds.NoPermissionError(channel);
                    }
                    break;

                default:
                    if (args.length >= 3)
                    {
                        //Create Question
                        CreateQuestionCommand(channel.getGuild(), member, channel, message, args);
                    }
                    else
                    {
                        //Usage
                        embeds.GeneralUsage(channel);
                    }
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
    private void CategoryCreateCommand(String[] args, Guild guild, TextChannel channel)
    {
        if (args.length == 4)
        {
            try
            {
                long categoryid = Long.parseLong(args[3]);
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
                embeds.ThisIsNoIDError(channel, args[3]);
            }
        }
        else
        {
            //Usage
            embeds.QuestionCategoryCreateUsage(channel);
        }
    }

    private void CategoryRemoveCommand(String[] args, Guild guild, TextChannel channel)
    {
        if (args.length == 3)
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

                    Runnable r = new DeleteQuestionRunnable(guild, channel, memberid, deletedInSeconds, sql);
                    new Thread(r).start();
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
        if (roles.size() <= mentionableRoles)
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

                String wholeQuestion = buildQuestionString(args, roles, 1);
                String questionTitle = "";
                String question = "";
                String roleString = createRoleString(roles);

                if (wholeQuestion.contains(questionKey))
                {
                    String[] questionParts = wholeQuestion.split(questionKey);
                    questionTitle = "**" + questionParts[0] + "**";
                    question = questionParts[1];
                }
                else
                {
                    question = wholeQuestion;
                }

                //Send Question Message
                EmbedBuilder builder = embeds.CreateQuestionEmbed(member, questionTitle, question, roleString);
                EmbedManager.SendEmbed(builder, textChannel, 0);

                //SQLDatabase
                long channelID = textChannel.getIdLong();
                long guildID = textChannel.getGuild().getIdLong();
                long memberID = member.getIdLong();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String creationTime = textChannel.getTimeCreated().toLocalDateTime().format(formatter);

                //SQL
                sql.AddGeneratedQuestionToSQL(channelID, guildID, memberID, creationTime);
            }
            else
            {
                //Error
                embeds.ThisServerHasNoExistingQuestionCategoryError(channel);
            }
        }
        else
        {
            embeds.YouCanOnlyMentionOneRoleInAQuestionError(channel, mentionableRoles);
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
