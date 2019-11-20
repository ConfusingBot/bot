package main.de.confusingbot.listener;

import main.de.confusingbot.roles.AcceptRules;
import main.de.confusingbot.roles.ReactRoles;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        ReactRoles.onReactionAdd(event);
        AcceptRules.onReactionAdd(event);

    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {

        ReactRoles.onReactionRemove(event);
        AcceptRules.onReactionRemove(event);

    }


}
