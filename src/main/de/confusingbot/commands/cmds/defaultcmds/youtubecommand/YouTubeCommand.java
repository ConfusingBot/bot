package main.de.confusingbot.commands.cmds.defaultcmds.youtubecommand;

import com.google.api.services.youtube.model.Activity;
import com.google.api.services.youtube.model.ActivityListResponse;
import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.youtubeapi.YouTubeAPIManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.time.LocalDateTime;


public class YouTubeCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    public YouTubeCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //- youtube new [channelID]
        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        if (args.length > 1)
        {
            switch (args[1])
            {
                case "now":
                    NewCommand(message, channel, args);
                    break;
                default:
                    //Usage
                    embeds.GeneralUsage(channel);
                    break;
            }
        }
        else
        {
            //Usage
            embeds.GeneralUsage(channel);
        }

    }

    //=====================================================================================================================================
    //Commands
    //=====================================================================================================================================
    private void NewCommand(Message message, TextChannel channel, String[] args)
    {
        if (args.length == 3)
        {
            String channelID = args[2];
            ActivityListResponse response = YouTubeAPIManager.getResponseById(channelID);
            if (response != null)
            {
                Activity newVideoActivity = response.getItems().get(0);

                String videoId = newVideoActivity.getContentDetails().getUpload().getVideoId();
                String url = "https://www.youtube.com/watch?v=" + videoId;
                LocalDateTime publishedAt = CommandsUtil.DateTimeConverter(newVideoActivity.getSnippet().getPublishedAt());
                String thumbnailUrl = newVideoActivity.getSnippet().getThumbnails().getHigh().getUrl();
                String title = newVideoActivity.getSnippet().getTitle();
                String uploaderName = newVideoActivity.getSnippet().getChannelTitle();

                //Message
                embeds.SendVideoEmbed(channel, url, title, thumbnailUrl, publishedAt, uploaderName);
            }
            else
            {
                //Error
                embeds.NoValidYouTubeIdError(channel, channelID);
            }
        }
        else
        {
            //Usage
            embeds.YouTubeNowUsage(channel);
        }

    }
}
