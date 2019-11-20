package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.strings.StringsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Strings
{

    public Strings()
    {
        HelpManager.admin.add("```fix\n" + Main.prefix + "acceptrule [#channel] [messageID] [emote] [@role rules not accepted] [@role rules accepted]\n``` " +
                "```▶ ️Create a [#channel] where you wirte your rule message [messageID]\n" +
                "▶ ️Create a @role [role rules not accepted] which you deny by every Voice/TextChannel, \n" +
                "▶️ Create another @role [role rules accepted]\n" +
                "▶️ Think about a [emote] with which you want to \"unlock\" the channels```" +
                "```Add a emotji to your rules on which the user can click and automaticlly unlock special channels```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule add/remove`", channel, StringsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules not accepted] [@role rules accepted]`", channel, StringsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule remove`", channel, StringsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        StringsUtil.NoValidIDNumberError(channel, id);
    }

    public void OnlyOneAcceptRuleAllowedError(TextChannel channel)
    {
        StringsUtil.OnlyOneAllowedToExistError(channel, "AcceptRule");
    }

    public void NoExistingAcceptRuleError(TextChannel channel)
    {
        StringsUtil.NotExistingError(channel, "AcceptRule");
    }

    public void NoPermissionError(TextChannel channel)
    {
        StringsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedAcceptRule(TextChannel channel)
    {
        StringsUtil.SuccessfulAdded(channel, "AcceptRule", "this server");
    }

    public void SuccessfulRemovedAcceptRule(TextChannel channel)
    {
        StringsUtil.SuccessfulRemoved(channel, "AcceptRule", "this server");
    }

}
