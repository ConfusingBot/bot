package de.confusingbot.commands.cmds.ownercmds.leavecommand;

import de.confusingbot.commands.help.EmbedsUtil;
import de.confusingbot.manage.embeds.EmbedManager;
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
