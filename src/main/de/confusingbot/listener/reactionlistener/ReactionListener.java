package main.de.confusingbot.listener.reactionlistener;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesListener;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    ReactRolesListener reactRolesListener = new ReactRolesListener();
    AcceptRulesListener acceptRulesListener = new AcceptRulesListener();

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        reactRolesListener.onReactionAdd(event);
        acceptRulesListener.onReactionAdd(event);

    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {

        reactRolesListener.onReactionRemove(event);
        acceptRulesListener.onReactionRemove(event);

    }


}
