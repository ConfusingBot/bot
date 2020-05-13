package main.de.confusingbot.commands.cmds.defaultcmds.WeatherCommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import main.de.confusingbot.manage.person.Person;
import main.de.confusingbot.manage.person.PersonManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class WeatherCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    //Icon
    //https://www.iconfinder.com/iconsets/weather-filled-outline-6

    public WeatherCommand()
    {
        embeds.HelpEmbed();
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message)
    {
        //Get Bot
        bot = channel.getGuild().getSelfMember();

        String[] args = CommandsUtil.messageToArgs(message);
        EmbedManager.DeleteMessageByID(channel, message.getIdLong());

        //https://openweathermap.org/current
        String WEATHER_KEY = "38e296d73fe637ed9bd4a6522e447bb3";

        if (bot.hasPermission(channel, MESSAGE_WRITE))
        {
            if (args.length > 1)
            {
                //Get City
                String city = "";
                String wordSeparator = " ";
                for (int i = 1; i < args.length; i++)
                {
                    city += args[i] + wordSeparator;
                }
                city = city.substring(0, city.length() - wordSeparator.length());

                try
                {
                    //Check Url
                    URL weatherUrl = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + WEATHER_KEY);
                    HttpURLConnection huc = (HttpURLConnection) weatherUrl.openConnection();
                    int responseCode = huc.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {
                        //Get JSON-Object
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(weatherUrl.openConnection().getInputStream()));
                        String jsonString = bufferedReader.readLine();
                        JSONObject jsonObject = new JSONObject(jsonString);

                        JSONArray weather = jsonObject.getJSONArray("weather");
                        String main = weather.getJSONObject(0).getString("main");
                        String description = weather.getJSONObject(0).getString("description");
                        double tempInDegree = (int) ((jsonObject.getJSONObject("main").getDouble("temp") - 273.15) * 100) / 100d;
                        double windSpeed = (int) ((jsonObject.getJSONObject("wind").getDouble("speed") * 1.621371) * 100) / 100d;
                        double windDeg = jsonObject.getJSONObject("wind").getDouble("deg");

                        //Message
                        embeds.SendWeather(channel, city, description, tempInDegree, windSpeed, windDeg, "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-02-128.png");
                    }
                    else
                    {
                        //Error
                        embeds.LocationDoesNotExistInformation(channel, city);
                    }
                } catch (Exception e)
                {
                    embeds.SendSomethingWentWrong(channel);
                }
            }
            else
            {
                //Usage
                embeds.WeatherUsage(channel);
            }

        }
    }
}
