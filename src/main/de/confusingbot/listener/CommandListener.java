package main.de.confusingbot.listener;

import main.de.confusingbot.Main;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.isFromType(ChannelType.TEXT)) {
            String message = event.getMessage().getContentDisplay();
            TextChannel channel = event.getTextChannel();

            if(message.startsWith(Main.prefix)) {

                String[] args = message.substring(Main.prefix.length()).split(" ");

                if(args.length > 0) {
                    if(!Main.INSTANCE.getCmdManager().perform(args[0], event.getMember(), channel, event.getMessage())) {
                        channel.sendMessage("`Unknown Comment!`").queue();;
                    }
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event){

            String message = event.getMessage().getContentDisplay();
            PrivateChannel channel = event.getChannel();

            if(message.startsWith(Main.prefix)) {

                String[] args = message.substring(Main.prefix.length()).split(" ");

                if(args.length > 0) {
                    if(!Main.INSTANCE.getCmdManager().performPrivate(args[0], event.getAuthor(), channel, event.getMessage())) {
                        channel.sendMessage("`Unknown Comment!`").queue();;
                    }
                }

            }

    }

}

