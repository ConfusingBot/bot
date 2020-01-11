package main.de.confusingbot.commands.cmds.defaultcmds.infocommand;

import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.BotInfo;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.ClientInfo;
import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos.ServerInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InfoCommandManager
{

    public static SQL sql = new SQL();
    public static Embeds embeds = new Embeds();
    public static String noInformationString = "none";

    public static BotInfo botInfo = new BotInfo();
    public static ClientInfo clientInfo = new ClientInfo();
    public static ServerInfo serverInfo = new ServerInfo();

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
    public static String defaultOnlineTime = "0, 0, 0, 0, 0, 0";

}
