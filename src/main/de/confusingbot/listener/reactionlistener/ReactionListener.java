package main.de.confusingbot.listener.reactionlistener;

import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesListener;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        ReactRolesListener.onReactionAdd(event);
        AcceptRulesListener.onReactionAdd(event);

    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {

        ReactRolesListener.onReactionRemove(event);
        AcceptRulesListener.onReactionRemove(event);

    }


}
