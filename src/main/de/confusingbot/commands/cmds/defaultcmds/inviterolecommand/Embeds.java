package main.de.confusingbot.commands.cmds.defaultcmds.inviterolecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.EmbedsUtil;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Invite;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "invite```\n"
                + "```Get special roles by inviting as many people as possible^^```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void InviteAddUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "invite add [@role] [inviteCount]`", channel, EmbedsUtil.showUsageTime);
    }

    public void InviteRemoveUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("`" + Main.prefix + "invite remove [@role]`", channel, EmbedsUtil.showUsageTime);
    }

    public void GeneralUsage(TextChannel channel)
    {
        EmbedManager.SendUsageEmbed(
                "```yaml\n" + Main.prefix + "invite add [@role] [inviteCount]\n```"
                        + "```Add @role to the InviteRole```"
                        + "```yaml\n" + Main.prefix + "invite remove [@role]\n```"
                        + "```Remove @role from the InviteRoles```"
                        + "```yaml\n" + Main.prefix + "invite info\n```"
                        + "```Shows you how to get a special InviteRole on this server```"
                        + "```yaml\n" + Main.prefix + "invite stats\n```"
                        + "```Shows your invite stats```"
                        + "```yaml\n" + Main.prefix + "leaderboard\n```"
                        + "```Shows you the top inviter of this server```", channel, main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void NoPermissionError(TextChannel channel, Permission permission)
    {
        EmbedsUtil.NoPermissionError(channel, permission);
    }

    public void BotHasNoPermissionToAssignRole(TextChannel channel, Role role)
    {
        EmbedManager.SendErrorEmbed("The bot has no right to assign this " + role.getAsMention() + "\n" +
                "Please give the bot a role over the role to be assign this role!", channel, main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil.showErrorTime);
    }

    public void RoleDoesNotExistError(TextChannel channel, long roleid)
    {
        EmbedManager.SendErrorEmbed("The role with the id " + roleid + " doesn't exist on this server!", channel, main.de.confusingbot.commands.cmds.admincmds.EmbedsUtil.showErrorTime);
    }

    public void NoMentionedRoleError(TextChannel channel)
    {
        EmbedsUtil.HavenNotMentionedError(channel, "@role");
    }

    public void NoNumberError(TextChannel channel, String numberString)
    {
        EmbedsUtil.NoNumberError(channel, numberString);
    }

    //=====================================================================================================================================
    //Success
    //=====================================================================================================================================
    public void SuccessfullyAddedInviteRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You successfully added " + role.getAsMention() + " to the InviteRoles", channel, EmbedsUtil.showSuccessTime);
    }

    public void SuccessfullyRemovedInviteRole(TextChannel channel, Role role)
    {
        EmbedManager.SendSuccessEmbed("You successfully removed " + role.getAsMention() + " from the InviteRoles", channel, EmbedsUtil.showSuccessTime);
    }


    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendInviteLeaderBoard(TextChannel channel, String description)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#a1f542"));
        builder.setTitle("LeaderBoard");
        builder.setDescription(description);

        EmbedManager.SendEmbed(builder, channel, 10);
    }

    public void SendInfo(TextChannel channel, String list)
    {
        EmbedBuilder builder = new EmbedBuilder();

        String listString = "";
        if (list != null && !list.equals(""))
        {
            listString = list;
        }
        else
        {
            listString = "This server does not support invite roles!";
        }

        builder.setTitle("\uD83D\uDD0EInformation");
        builder.setColor(Color.decode("#60f4b4"));
        builder.setDescription("**Do you want to get special roles?** \n" + listString + "\n\n **Than create a infinity InviteLink and invite as many people as possible\uD83D\uDCDE**\n (Note that temporary will not count\uD83E\uDD14)");

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 10);
    }

    public void SendListEmbed(TextChannel channel, String list)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setTitle("InviteRoleList");
        builder.setColor(Color.decode("#60f4b4"));
        builder.setDescription(list);

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 10);
    }

    public void SendUserInviteStats(TextChannel channel, int uses, LocalDateTime creationDate, Member member, String inviteLink, Invite.Channel inviteChannel)
    {
        DateTimeFormatter creationDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String creationDateString = creationDateFormatter.format(creationDate);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#60f4b4"));
        builder.setTitle("Invite Stats from " + member.getNickname());
        builder.addField("Uses", Integer.toString(uses), true);
        builder.addField("Creation Date", creationDateString, true);
        builder.addField("InviteLink", inviteLink, true);
        builder.addField("TextChannel", inviteChannel.getName(), true);

        //Send Embed
        EmbedManager.SendEmbed(builder, channel, 10);
    }

}
