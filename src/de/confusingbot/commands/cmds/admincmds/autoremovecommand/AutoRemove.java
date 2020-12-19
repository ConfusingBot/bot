package de.confusingbot.commands.cmds.admincmds.autoremovecommand;

import net.dv8tion.jda.api.entities.Guild;

public class AutoRemove
{
    Guild guild;
    boolean canAutoRemove = true;

    public AutoRemove(Guild guild)
    {
        this.guild = guild;
    }

    public boolean getCanAutoRemove()
    {
        return canAutoRemove;
    }

    public void setCanAutoRemove(boolean canAutoRemove)
    {
        this.canAutoRemove = canAutoRemove;
    }
}
