package de.confusingbot.commands.types;

import net.dv8tion.jda.api.entities.*;

public interface PrivateCommand {

    public void performPrivateCommand(User user, PrivateChannel channel, Message message);

}
