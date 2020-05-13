package main.de.confusingbot.commands.cmds.defaultcmds.WeatherCommand;

import main.de.confusingbot.Main;
import main.de.confusingbot.commands.cmds.defaultcmds.helpcommand.HelpManager;
import main.de.confusingbot.commands.help.EmbedsUtil;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.person.Person;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds
{
    public void HelpEmbed()
    {
        HelpManager.useful.add("```yaml\n" + Main.prefix + "weather [city]\n``` ```Will give you some weather information!```");
    }

    //=====================================================================================================================================
    //Usage
    //=====================================================================================================================================
    public void WeatherUsage(TextChannel channel)
    {
        EmbedManager.SendInfoEmbed("```yaml\n" + Main.prefix + "weather [city]\n``` ```Will give you some weather information!```", channel, EmbedsUtil.showUsageTime);
    }

    //=====================================================================================================================================
    //Error
    //=====================================================================================================================================
    public void SendSomethingWentWrong(TextChannel channel)
    {
        EmbedManager.SendErrorEmbed("Something went wrong :/", channel, EmbedsUtil.showErrorTime);
    }

    //=====================================================================================================================================
    //Information
    //=====================================================================================================================================
    public void LocationDoesNotExistInformation(TextChannel channel, String location)
    {
        EmbedManager.SendInfoEmbed("Couldn't find `" + location + "`!", channel, EmbedsUtil.showInfoTime);
    }

    //=====================================================================================================================================
    //Other
    //=====================================================================================================================================
    public void SendWeather(TextChannel channel, String city, String description, double tempInDegree, double windSpeed, double windDeg, String imageUrl)
    {
        EmbedBuilder builder = new EmbedBuilder();

        builder.setColor(Color.decode("#32CD32"));
        builder.addField("Description", description, false);
        builder.addField("Degree", tempInDegree + "°C", false);
        builder.addField("Wind Speed", windSpeed + "km/h", true);
        builder.addField("Wind Degree", windDeg + "°", true);
        builder.setTitle("\uD83C\uDFD9 " + city);
        builder.setThumbnail(imageUrl);


        EmbedManager.SendEmbed(builder, channel, 30);
    }

}
