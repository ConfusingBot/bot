package main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ServerInfo
{
    public File createChartFile(List<Integer> values, List<String> dates)
    {
         /*
        //TODO DELETE!------------------------------------------------------------------
        values.clear();
        dates.clear();
        Random random = new Random();
        int day = 1;
        int month = 1;
        for (int i = 1; i < 3 + 1; i++)
        {
            if (i > 25 && i < 50)
            {
                values.add(random.nextInt(80) + 50);
            }
            else if (i > 50 && i < 75)
            {
                values.add(random.nextInt(100) + 90);
            }
            else if (i > 75)
            {
                values.add(random.nextInt(150) + 120);
            }
            else
            {
                values.add(random.nextInt(40) + 1);
            }

            dates.add((day <= 9 ? "0" + day : day) + "/" + (month <= 9 ? "0" + month : month) + "/2020");

            if (i % 30 == 0)
            {
                day = 1;
                month++;
            }
            else
            {
                day++;
            }
        }
        //TODO DELETE!------------------------------------------------------------------
        */

        //Create Date
        List<Date> date = new ArrayList<>();

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (String dateString : dates)
        {
            try
            {
                date.add(formatter.parse(dateString));
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
        }

        //Create File
        Random r = new Random();
        String imageName = "serverPanel" + r.nextInt(100000) + 1 + ".png";
        File file = new File(imageName);

        //==============================================================================================================
        // Create Chart
        //==============================================================================================================
        final XYChart chart = new XYChartBuilder().width(1920).height(400)
                .xAxisTitle("Time").yAxisTitle("Members")
                .build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setLegendVisible(false);

        chart.getStyler().setPlotMargin(10);
        //chart.getStyler().setXAxisTickMarkSpacingHint(3000 / date.size());//The space between the x-Axis values

        chart.getStyler().setDatePattern("dd/MMM");

        chart.getStyler().setPlotBackgroundColor(Color.white);
        chart.getStyler().setChartBackgroundColor(Color.white);

        // Series
        XYSeries series = chart.addSeries("members", date, values);
        series.setSmooth(true);
        //==============================================================================================================
        // End Create Chart
        //==============================================================================================================

        // Save Chart as File
        try
        {
            BitmapEncoder.saveBitmap(chart, file.getPath(), BitmapEncoder.BitmapFormat.PNG);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    public int getBots(Guild guild)
    {
        List<Member> members = guild.getMembers();
        int count = 0;

        for (Member member : members) if (member.getUser().isBot()) count++;

        return count;
    }
}
