package main.de.confusingbot.commands.cmds.defaultcmds.WeatherCommand;

import main.de.confusingbot.commands.help.CommandsUtil;
import main.de.confusingbot.manage.json.JsonReader;
import main.de.confusingbot.commands.types.ServerCommand;
import main.de.confusingbot.manage.embeds.EmbedManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class WeatherCommand implements ServerCommand
{

    Embeds embeds = new Embeds();

    Member bot;

    //Needed Permissions
    Permission MESSAGE_WRITE = Permission.MESSAGE_WRITE;

    //Icon
    //https://www.iconfinder.com/iconsets/weather-filled-outline-6

    public ConcurrentHashMap<String, String> icons = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, String> colors = new ConcurrentHashMap<>();

    public WeatherCommand()
    {
        embeds.HelpEmbed();

        //Instantiate Icons
        icons.put("01d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-49-128.png");
        icons.put("02d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-04-128.png");
        icons.put("03d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-10-128.png");
        icons.put("04d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-41-128.png");
        icons.put("09d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-02-128.png");
        icons.put("10d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-03-128.png");
        icons.put("11d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-09-128.png");
        icons.put("13d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-22-128.png");
        icons.put("50d", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-17-128.png");

        icons.put("01n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-30-128.png");
        icons.put("02n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-11-128.png");
        icons.put("03n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-12-128.png");
        icons.put("04n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-25-128.png");
        icons.put("09n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-02-128.png");
        icons.put("10n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-14-128.png");
        icons.put("11n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-15-128.png");
        icons.put("13n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-24-128.png");
        icons.put("50n", "https://cdn0.iconfinder.com/data/icons/weather-filled-outline-6/64/weather_cloud_sun_moon_rain-19-128.png");

        //Instantiate Colors
        colors.put("01d", "#87ceeb");
        colors.put("02d", "#87ceeb");
        colors.put("03d", "#87ceeb");
        colors.put("04d", "#87ceeb");
        colors.put("09d", "#87ceeb");
        colors.put("10d", "#87ceeb");
        colors.put("11d", "#87ceeb");
        colors.put("13d", "#87ceeb");
        colors.put("50d", "#87ceeb");

        colors.put("01n", "#05181f");
        colors.put("02n", "#05181f");
        colors.put("03n", "#05181f");
        colors.put("04n", "#05181f");
        colors.put("09n", "#05181f");
        colors.put("10n", "#05181f");
        colors.put("11n", "#05181f");
        colors.put("13n", "#05181f");
        colors.put("50n", "#05181f");

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
                //Send WaitMessage
                long waitMessageId = embeds.SendWaitMessage(channel);

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
                        JSONObject jsonObject = JsonReader.readJsonFromUrl(weatherUrl);

                        JSONArray weather = jsonObject.getJSONArray("weather");
                        String main = weather.getJSONObject(0).getString("main");
                        String description = weather.getJSONObject(0).getString("description");
                        String icon = weather.getJSONObject(0).getString("icon");
                        double tempInDegree = (int) ((jsonObject.getJSONObject("main").getDouble("temp") - 273.15) * 100) / 100d;
                        double feelsLikeTempInDegree = (int) ((jsonObject.getJSONObject("main").getDouble("feels_like") - 273.15) * 100) / 100d;
                        int humidity = jsonObject.getJSONObject("main").getInt("humidity");
                        double windSpeed = (int) ((jsonObject.getJSONObject("wind").getDouble("speed") * 1.621371) * 100) / 100d;

                        //Message
                        embeds.SendWeather(channel, city, description, tempInDegree, feelsLikeTempInDegree, windSpeed, humidity, icons.get(icon), colors.get(icon));
                    }
                    else
                    {
                        //Error
                        embeds.LocationDoesNotExistInformation(channel, city);
                    }
                } catch (Exception e)
                {
                    //Error
                    embeds.SendSomethingWentWrong(channel, 0);

                    throw new Error(e);
                }

                //Delete WaitMessage
                EmbedManager.DeleteMessageByID(channel, waitMessageId);
            }
            else
            {
                //Usage
                embeds.WeatherUsage(channel);
            }

        }
    }
}
