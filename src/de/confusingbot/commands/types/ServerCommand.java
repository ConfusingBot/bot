package de.confusingbot.commands.types;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Member;

public interface ServerCommand {

    public void performCommand(Member member, TextChannel channel, Message message);

}
