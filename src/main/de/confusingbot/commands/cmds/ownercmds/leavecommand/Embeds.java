package main.de.confusingbot.commands.cmds.ownercmds.leavecommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.cmds.ownercmds.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds
{

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void CouldNotFindGuildByIDError(TextChannel channel, String idString)
    {
        EmbedManager.SendErrorEmbed("Could not find Server by ID: " + idString + "!", channel, EmbedsUtil.showErrorTime);
    }

    public void NoValidIdError(TextChannel channel, String idString)
    {
        EmbedsUtil.NoValidIDNumberError(channel, idString);
    }

}
