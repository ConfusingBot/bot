package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{
    public Embeds()
    {
        HelpManager.admin.add("```yaml\n" + Main.prefix + "reactrole [channel] [messageID] [emote] [@role])\n``` " +
                "```▶️Create a [#channel] where you  write your RoleBoard [messageID]\n" +
                "▶️Think about a [emote] with which you want to get the [@role]```" +
                "```Add a emotji to you role board on which the user can click and automaticlly get the @role```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message welcome/leave`", channel, EmbedsUtil.showUsageTime);
    }

    public void WelcomeMessageUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message welcome [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
    }

    public void LeaveMessageUsage(TextChannel channel){
        EmbedManager.SendUsageEmbed("`" + Main.prefix + "message leave [Message (@newMember | #channel)]`", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel)
    {
        EmbedsUtil.NoPermissionError(channel);
    }



    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================

}
