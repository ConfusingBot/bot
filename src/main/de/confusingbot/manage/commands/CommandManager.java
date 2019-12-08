package main.de.confusingbot.manage.commands;

import main.de.confusingbot.commands.cmds.admincmds.customonetimeembedcommand.CustomOneTimeEmbedCommand;
import main.de.confusingbot.commands.cmds.admincmds.acceptrulecommand.AcceptRuleCommand;
import main.de.confusingbot.commands.cmds.admincmds.clearcommand.ClearCommand;
import main.de.confusingbot.commands.cmds.admincmds.messagecommand.MessageCommand;
import main.de.confusingbot.commands.cmds.admincmds.reactrolescommand.ReactRolesCommand;
import main.de.confusingbot.commands.cmds.admincmds.rolebordercommand.RoleBorderCommand;
import main.de.confusingbot.commands.cmds.admincmds.rolecommand.RoleCommand;
import main.de.confusingbot.commands.cmds.admincmds.tempvoicechannelcommand.TempVoiceChannelCommand;
import main.de.confusingbot.commands.cmds.admincmds.votecommand.VoteCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.hugcommand.HugCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.jokecommand.JokeCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.pingcommand.PingCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.previewcommand.PreviewCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.questioncommand.QuestionCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.reactcommand.ReactCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.rulescommand.RulesCommand;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.PrivateHelpCommand;
import main.de.confusingbot.commands.cmds.musiccmds.joincommand.JoinCommand;
import main.de.confusingbot.commands.cmds.musiccmds.leavecommand.LeaveCommand;
import main.de.confusingbot.commands.cmds.musiccmds.pausecommand.PauseCommand;
import main.de.confusingbot.commands.cmds.musiccmds.playcommand.PlayCommand;
import main.de.confusingbot.commands.cmds.musiccmds.queuecommand.QueueCommand;
import main.de.confusingbot.commands.cmds.musiccmds.shufflecommand.ShuffleCommand;
import main.de.confusingbot.commands.cmds.musiccmds.skipcommand.SkipCommand;
import main.de.confusingbot.commands.cmds.musiccmds.trackinfocommand.TrackInfoCommand;
import main.de.confusingbot.commands.cmds.ownercmds.leavecommand.LeaveServerCommand;
import main.de.confusingbot.commands.types.PrivateCommand;
import main.de.confusingbot.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.concurrent.ConcurrentHashMap;

public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;
    public ConcurrentHashMap<String, PrivateCommand> privateCommands;



    public CommandManager(){
        this.commands = new ConcurrentHashMap<>();
        this.privateCommands = new ConcurrentHashMap<>();

        this.commands.put("help", new HelpCommand());
        this.commands.put("preview", new PreviewCommand());
        this.commands.put("hug", new HugCommand());
        this.commands.put("reactrole", new ReactRolesCommand());
        this.commands.put("react", new ReactCommand());
        this.commands.put("info", new InfoCommand());
        this.commands.put("rules", new RulesCommand());
        this.commands.put("ping", new PingCommand());
        this.commands.put("joke", new JokeCommand());
        this.commands.put("question", new QuestionCommand());

        //Special Rights
        this.commands.put("onetimeembed", new CustomOneTimeEmbedCommand());
        this.commands.put("tempchannel", new TempVoiceChannelCommand());
        this.commands.put("role", new RoleCommand());
        this.commands.put("clear", new ClearCommand());
        this.commands.put("acceptrule", new AcceptRuleCommand());
        this.commands.put("roleborder", new RoleBorderCommand());
        this.commands.put("repeatinfo", new main.de.confusingbot.commands.cmds.admincmds.repeatinfocommand.InfoCommand());
        this.commands.put("message", new MessageCommand());
        this.commands.put("vote", new VoteCommand());

        //Music
        this.commands.put("play", new PlayCommand());
        this.commands.put("leave", new LeaveCommand());
        this.commands.put("trackinfo", new TrackInfoCommand());
        this.commands.put("shuffle", new ShuffleCommand());
        this.commands.put("join", new JoinCommand());
        this.commands.put("queue", new QueueCommand());
        this.commands.put("skip", new SkipCommand());
        this.commands.put("pause", new PauseCommand());

        //Owner
        this.commands.put("leaveserver", new LeaveServerCommand());

        //PRIVATE
        this.privateCommands.put("help", new PrivateHelpCommand());

    }

    public boolean perform(String command, Member member, TextChannel channel, Message message){

        ServerCommand cmd = this.commands.get(command.toLowerCase());
        if(cmd != null){
            cmd.performCommand(member, channel, message);
            return true;
        }
        return false;
    }

    public boolean performPrivate(String command, User user, PrivateChannel channel, Message message){
        PrivateCommand cmd = this.privateCommands.get(command.toLowerCase());
        if(cmd != null){
            cmd.performPrivateCommand(user, channel, message);
            return true;
        }
        return false;
    }

}
