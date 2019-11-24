package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "acceptrule``` " +
                "```Create a awesome rule system, where the user have to accept your rules to unlock the server```\n"
               );
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules not accepted] [@role rules accepted]\n```"
                +"```Add a emotji to your rules on which the user can click and automaticlly unlock special channels```\n"
                + "```yaml\n" + Main.prefix + "acceptrule remove\n```"
                +  "```Remove the AcceptRule```\n"
                , channel, EmbedsUtil.showUsageTime);
    }

    public void AddUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule add [#channel] [messageID] [emote] [@role rules not accepted] [@role rules accepted]`", channel, EmbedsUtil.showUsageTime);
    }

    public void RemoveUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule remove`", channel, EmbedsUtil.showUsageTime);
    }


    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void ThisIsNoIDError(TextChannel channel, String id)
    {
        EmbedsUtil.NoValidIDNumberError(channel, id);
    }

    public void OnlyOneAcceptRuleAllowedError(TextChannel channel)
    {
        EmbedsUtil.OnlyOneAllowedToExistError(channel, "AcceptRule");
    }

    public void NoExistingAcceptRuleError(TextChannel channel)
    {
        EmbedsUtil.NotExistingError(channel, "AcceptRule");
    }

    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfulAddedAcceptRule(TextChannel channel)
    {
        EmbedsUtil.SuccessfulAdded(channel, "AcceptRule", "this server");
    }

    public void SuccessfulRemovedAcceptRule(TextChannel channel)
    {
        EmbedsUtil.SuccessfulRemoved(channel, "AcceptRule", "this server");
    }

}
