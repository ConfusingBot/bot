package main.de.confusingbot.commands.cmds.defaultcmds.questioncommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.concurrent.TimeUnit;

public class DeleteQuestionRunnable implements Runnable
{
    Guild guild;
    TextChannel channel;
    long memberid;
    int deletedInSeconds;
    SQL sql;
    public DeleteQuestionRunnable(Guild guild, TextChannel channel, long memberid, int deletedInSeconds, SQL sql){
       this.guild = guild;
       this.channel = channel;
       this.memberid = memberid;
       this.deletedInSeconds = deletedInSeconds;
       this.sql = sql;
    }

    @Override
    public void run()
    {
        sleepXSeconds(deletedInSeconds);

        //SQL
        sql.RemoveQuestionChannelFromSQL(guild.getIdLong(), channel.getIdLong(), memberid);

        //Delete Channel
        channel.delete().queue();
    }

    public void sleepXSeconds(float seconds)
    {
        int milliseconds = (int) (seconds * 1000);
        try
        {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
