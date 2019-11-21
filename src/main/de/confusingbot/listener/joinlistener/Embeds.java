package main.de.confusingbot.listener.joinlistener;

import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds {

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendDefaultWelcomeMessage(TextChannel channel, TextChannel rules, Member member) {
        if (channel != null) {
            EmbedManager.SendCustomEmbed("\uD83C\uDF89", "**Welcome** " + member.getAsMention() +
                    "\n\n**Please accept the**" + rules.getAsMention() + "\n" +
                    "**and enjoy your time** \uD83C\uDFDD", Color.ORANGE, channel, 0);
        }
    }
}
