package main.de.confusingbot.listener.commandlistener;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class Embeds {

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void CommandDoesNotExistsInformation(TextChannel channel){
        channel.sendMessage("`Unknown Comment!`").queue();;
    }

    public void CommandDoesNotExistsPrivateInformation(PrivateChannel channel){
        channel.sendMessage("`Unknown Comment!`").queue();;
    }
}
