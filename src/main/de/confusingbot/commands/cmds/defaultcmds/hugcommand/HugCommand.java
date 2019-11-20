package main.de.confusingbot.commands.cmds.defaultcmds.hugcommand;

import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class HugCommand implements ServerCommand {

    Strings strings = new Strings();

    private ConcurrentHashMap<Long, Long> timestamps;//save when the message was sent from which id
    String[] messages = new String[] {"come over %member\uD83E\uDDDF", "%member hugs himself\uD83D\uDE1C", "%member hugs ConfusingBot\uD83C\uDF08"};
    private double waitTime = 30000.0d;

    public HugCommand(){
        this.timestamps = new ConcurrentHashMap<>();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

       long id = member.getIdLong();
       if(timestamps.containsKey(id)){
           long time = timestamps.get(id);
           long waitedTime = System.currentTimeMillis() - time;

           if(waitedTime >= waitTime){
               this.timestamps.put(id, System.currentTimeMillis());
               send(member, channel, message);
           }else{
               DecimalFormat df = new DecimalFormat("0.00");
               double timeLeft = (waitTime - waitedTime) / 1000.0d;
               channel.sendMessage("You have to wait `" + df.format(timeLeft) + "` seconds⏰").queue();
           }
       }else{
           this.timestamps.put(id, System.currentTimeMillis());
           send(member, channel, message);
       }
    }

    private void send(Member member, TextChannel channel, Message message) {
        Random rand = new Random();
        int i = rand.nextInt(messages.length);
        String text = messages[i].replaceAll("%member", "" + member.getAsMention());
        channel.sendMessage(text).queue();
    }
}
