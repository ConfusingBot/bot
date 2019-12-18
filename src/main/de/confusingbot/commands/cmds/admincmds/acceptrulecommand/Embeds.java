package main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{

    public void HelpEmbed()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "acceptrule``` " +
                "```Create a awesome rule system, where the user has to accept your rules to unlock the server```\n"
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
                        + "```yaml\n" + Main.prefix + "acceptrule show\n```"
                        +  "```Shows the AcceptedRule of this server```\n"
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

    public void ShowUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "acceptrule show`", channel, EmbedsUtil.showUsageTime);
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

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void ServerHasNoAcceptedRuleInformation(TextChannel channel){
        EmbedManager.SendUsageEmbed("This server has no **AcceptedRule** you can add one with `add`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void ShowAcceptRule(TextChannel channel, String textChannelString, String notAcceptedRoleString, String acceptedRoleString, String emote){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#15d1cb"));
        builder.setTitle("\uD83D\uDC51AcceptedRule: ");
        builder.addField("TextChannel", textChannelString, false);
        builder.addField("NotAcceptedRole", notAcceptedRoleString, false);
        builder.addField("AcceptedRole", acceptedRoleString, false);
        builder.addField("Emote", emote, false);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
