package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeoutException;

public class Strings
{

    public Strings()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "question [Title] QUESTION: [Question] [mentioned role]\n```" +
                " ```Create a question in a custom Channel where only your question exists```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "question [Title] QUESTION: [Question] [mentioned role]`", channel, StringsUtil.showUsageTime);
    }

    public void QuestionCloseUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "question close`", channel, StringsUtil.showUsageTime);
    }

    public void QuestionCategoryCreateUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "question category create [CategoryID]`", channel, StringsUtil.showUsageTime);
    }

    public void QuestionCategoryRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "question category remove`", channel, StringsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        StringsUtil.NoValidIDNumberError(channel, id);
    }

    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    public void OnlyOneAllowedQuestionCategory(TextChannel channel)
    {
        StringsUtil.OnlyOneAllowedToExistError(channel, "QuestionCategory");
    }

    public void NoPermissionForClosingThisQuestionChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You have no permission to close this question!", channel, StringsUtil.showErrorTime);
    }

    public void YouAreNotInAQuestionChannelError(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("You can only close a question in a QuestionChannel!", channel, StringsUtil.showErrorTime);
    }

    public void YouCanOnlyMentionOneRoleInAQuestionError(TextChannel channel, int mentionableRoles)
    {
        EmbedManager.SendInfoEmbed("Sry you can only mention **" + mentionableRoles + " roles**!", channel, StringsUtil.showErrorTime);
    }

    public void ThisServerHasNoExistingQuestionCategoryError(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("**This doesn't support this feature!**\nInform a **Admin** about that!", channel, StringsUtil.showErrorTime);
    }

    public void ServerHasNoQuestionCategoryError(TextChannel channel)
    {
        StringsUtil.NotExistingError(channel, "QuestionCategory");
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedCategoryToQuestionCategory(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully create a QuestionCategory called" + name, channel, StringsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedCategoryToQuestionCategory(TextChannel channel, String name)
    {
        EmbedManager.SendSuccessEmbed("You successfully removed a QuestionCategory called" + name, channel, StringsUtil.showSuccessTime);
    }

    //=====================================================================================================================================
    //Info
    //=====================================================================================================================================
    public static void QuestionChannelWillBeDeletedInXSeconds(TextChannel channel, int deletedInSeconds)
    {
        EmbedManager.SendInfoEmbed("The question will be deleted in " + deletedInSeconds + "s!", channel, 0);
    }
}
