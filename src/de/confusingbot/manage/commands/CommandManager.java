package de.confusingbot.manage.commands;

import de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleCommand;
import de.confusingbot.commands.cmds.admincmds.autoremovecommand.AutoRemoveCommand;
import de.confusingbot.commands.cmds.admincmds.clearcommand.ClearCommand;
import de.confusingbot.commands.cmds.admincmds.eventcommand.EventCommand;
import de.confusingbot.commands.cmds.admincmds.joinrole.JoinRoleCommand;
import de.confusingbot.commands.cmds.admincmds.messagecommand.MessageCommand;
import de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesCommand;
import de.confusingbot.commands.cmds.admincmds.repeatinfocommand.RepeatInfoCommand;
import de.confusingbot.commands.cmds.admincmds.rolebordercommand.RoleBorderCommand;
import de.confusingbot.commands.cmds.admincmds.rolecommand.RoleCommand;
import de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand.TempVoiceChannelCommand;
import de.confusingbot.commands.cmds.admincmds.votecommand.VoteCommand;
import de.confusingbot.commands.cmds.defaultcmds.WeatherCommand.WeatherCommand;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpCommand;
import de.confusingbot.commands.cmds.defaultcmds.helpcommand.PrivateHelpCommand;
import de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommand;
import de.confusingbot.commands.cmds.defaultcmds.inviterolecommand.InviteRoleCommand;
import de.confusingbot.commands.cmds.defaultcmds.pingcommand.PingCommand;
import de.confusingbot.commands.cmds.defaultcmds.previewcommand.PreviewCommand;
import de.confusingbot.commands.cmds.defaultcmds.questioncommand.QuestionCommand;
import de.confusingbot.commands.cmds.defaultcmds.reactcommand.ReactCommand;
import de.confusingbot.commands.cmds.defaultcmds.youtubecommand.YouTubeCommand;
import de.confusingbot.commands.cmds.funcmds.advicecommand.AdviceCommand;
import de.confusingbot.commands.cmds.funcmds.catcommand.CatCommand;
import de.confusingbot.commands.cmds.funcmds.hugcommand.HugCommand;
import de.confusingbot.commands.cmds.funcmds.jokecommand.JokeCommand;
import de.confusingbot.commands.cmds.funcmds.memecommand.MemeCommand;
import de.confusingbot.commands.cmds.funcmds.yodaCommand.YodaCommand;
import de.confusingbot.commands.cmds.musiccmds.joincommand.JoinCommand;
import de.confusingbot.commands.cmds.musiccmds.leavecommand.LeaveCommand;
import de.confusingbot.commands.cmds.musiccmds.pausecommand.PauseCommand;
import de.confusingbot.commands.cmds.musiccmds.playcommand.PlayCommand;
import de.confusingbot.commands.cmds.musiccmds.queuecommand.QueueCommand;
import de.confusingbot.commands.cmds.musiccmds.skipcommand.SkipCommand;
import de.confusingbot.commands.cmds.musiccmds.trackinfocommand.TrackInfoCommand;
import de.confusingbot.commands.cmds.musiccmds.volumecommand.VolumeCommand;
import de.confusingbot.commands.cmds.ownercmds.customonetimeembedcommand.CustomOneTimeEmbedCommand;
import de.confusingbot.commands.cmds.ownercmds.leavecommand.LeaveServerCommand;
import de.confusingbot.commands.cmds.ownercmds.listcommand.ListServerCommand;
import de.confusingbot.commands.cmds.ownercmds.sendcommand.SendCommand;
import de.confusingbot.commands.types.PrivateCommand;
import de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager
{
    public ConcurrentHashMap<String, ServerCommand> commands;
    public ConcurrentHashMap<String, PrivateCommand> privateCommands;


    public CommandManager()
    {
        this.commands = new ConcurrentHashMap<>();
        this.privateCommands = new ConcurrentHashMap<>();

        this.commands.put("help", new HelpCommand());
        this.commands.put("preview", new PreviewCommand());
        this.commands.put("hug", new HugCommand());
        this.commands.put("reactrole", new ReactRolesCommand());
        this.commands.put("react", new ReactCommand());
        this.commands.put("info", new InfoCommand());
        this.commands.put("ping", new PingCommand());
        this.commands.put("question", new QuestionCommand());
        this.commands.put("joinrole", new JoinRoleCommand());
        this.commands.put("invite", new InviteRoleCommand());
        this.commands.put("youtube", new YouTubeCommand());
        this.commands.put("weather", new WeatherCommand());

        //Fun
        this.commands.put("joke", new JokeCommand());
        this.commands.put("meme", new MemeCommand());
        this.commands.put("advice", new AdviceCommand());
        this.commands.put("cat", new CatCommand());
        this.commands.put("yoda", new YodaCommand());

        //Special Rights
        this.commands.put("onetimeembed", new CustomOneTimeEmbedCommand());
        this.commands.put("autoremove", new AutoRemoveCommand());
        this.commands.put("tempchannel", new TempVoiceChannelCommand());
        this.commands.put("role", new RoleCommand());
        this.commands.put("clear", new ClearCommand());
        this.commands.put("acceptrule", new AcceptRuleCommand());
        this.commands.put("roleborder", new RoleBorderCommand());
        this.commands.put("repeatinfo", new RepeatInfoCommand());
        this.commands.put("message", new MessageCommand());
        this.commands.put("vote", new VoteCommand());
        this.commands.put("event", new EventCommand());

        //Music
        this.commands.put("play", new PlayCommand());
        this.commands.put("leave", new LeaveCommand());
        this.commands.put("trackinfo", new TrackInfoCommand());
        this.commands.put("join", new JoinCommand());
        this.commands.put("queue", new QueueCommand());
        this.commands.put("skip", new SkipCommand());
        this.commands.put("pause", new PauseCommand());
        this.commands.put("volume", new VolumeCommand());

        //Owner
        this.commands.put("leaveserver", new LeaveServerCommand());
        this.commands.put("listserver", new ListServerCommand());
        this.commands.put("send", new SendCommand());

        //PRIVATE
        this.privateCommands.put("help", new PrivateHelpCommand());
    }

    public boolean perform(String command, Member member, TextChannel channel, Message message)
    {

        ServerCommand cmd = this.commands.get(command.toLowerCase());
        if (cmd != null)
        {
            cmd.performCommand(member, channel, message);
            return true;
        }
        return false;
    }

    public boolean performPrivate(String command, User user, PrivateChannel channel, Message message)
    {
        PrivateCommand cmd = this.privateCommands.get(command.toLowerCase());
        if (cmd != null)
        {
            cmd.performPrivateCommand(user, channel, message);
            return true;
        }
        return false;
    }

}
