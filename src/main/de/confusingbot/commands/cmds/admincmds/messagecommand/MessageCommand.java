package main.de.confusingbot.commands.cmds.admincmds.messagecommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MessageCommand implements ServerCommand {

    Embeds embeds = new Embeds();
    SQL sql = new SQL();

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        //- message welcome welcome @newMember to the server look at #rule

        String[] args = CommandsUtil.messageToArgs(message);
        message.delete().queue();

        if (member.hasPermission(Permission.ADMINISTRATOR)) {
            if (args.length >= 2) {

                switch (args[2]) {
                    case "welcome":
                        WelcomeMessageCommand(channel, args);
                        break;
                    case "leave":
                        LeaveMessageCommand(channel, args);
                        break;
                    default:
                        //Usage
                        embeds.GeneralUsage(channel);
                        break;
                }

            } else {
                //Usage
                embeds.GeneralUsage(channel);
            }
        } else {
            //Error
            embeds.NoPermissionError(channel);
        }
    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void WelcomeMessageCommand(TextChannel channel, String[] args) {
        if (args.length > 2) {

        } else {
            //Usage
            embeds.WelcomeMessageUsage(channel);
        }
    }

    private void LeaveMessageCommand(TextChannel channel, String[] args) {
        if (args.length > 2) {

        } else {
            //Usage
            embeds.LeaveMessageUsage(channel);
        }
    }


}
