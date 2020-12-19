package de.confusingbot.listener.reactionlistener;

import de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRulesListener;
import de.confusingbot.commands.cmds.admincmds.eventcommand.EventCommandListener;
import de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesListener;
import de.confusingbot.commands.cmds.admincmds.votecommand.VoteCommandListener;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReactionListener extends ListenerAdapter {

    ReactRolesListener reactRolesListener = new ReactRolesListener();
    AcceptRulesListener acceptRulesListener = new AcceptRulesListener();
    VoteCommandListener voteCommandListener = new VoteCommandListener();
    EventCommandListener eventCommandListener = new EventCommandListener();

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        reactRolesListener.onReactionAdd(event);
        acceptRulesListener.onReactionAdd(event);
        voteCommandListener.onReactionAdd(event);
        eventCommandListener.onReactionAdd(event);

    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {

        reactRolesListener.onReactionRemove(event);
        acceptRulesListener.onReactionRemove(event);
        eventCommandListener.onReactionRemove(event);

    }


}
