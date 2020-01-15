package main.de.confusingbot.commands.cmds.defaultcmds.infocommand.infos;

import main.de.confusingbot.commands.cmds.defaultcmds.infocommand.InfoCommandManager;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class ServerInfo
{
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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

        //Add Fill-Dates
        int marksLeft = 13 - dates.size();
        if (marksLeft > 0)
        {
            for (int i = 0; i < marksLeft; i++)
            {
                //Date
                try
                {
                    Date oldDate = formatter.parse(dates.get(0));

                    LocalDate newDate = oldDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().minusDays(1);

                    dates.add(0, formatter.format(Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant())));

                    //Value
                    values.add(0, values.get(0));
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        }

        //Create Date
        List<Date> date = new ArrayList<>();
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
        final XYChart chart = new XYChartBuilder().width(1080).height(400)
                .xAxisTitle("Time").yAxisTitle("Members")
                .build();

        Color backgroundColor = Color.decode("#2f3136");
        Color lineColor = Color.decode("#7289da");
        Color fillColor = new Color(0.114f, 0.137f, 0.218f, 0.5f);
        Color textColor = Color.WHITE;

        //Grid
        chart.getStyler().setPlotGridLinesVisible(false);
        chart.getStyler().setPlotGridHorizontalLinesVisible(true);

        //Grid Color
        chart.getStyler().setPlotGridLinesColor(textColor);
        chart.getStyler().setChartFontColor(textColor);
        chart.getStyler().setAnnotationsFontColor(textColor);
        chart.getStyler().setAxisTickLabelsColor(textColor);
        chart.getStyler().setPlotBorderColor(textColor);
        chart.getStyler().setAxisTickMarksColor(textColor);

        chart.getStyler().setLegendVisible(false);

        chart.getStyler().setDatePattern("dd/MMM");

        //Background
        chart.getStyler().setPlotBackgroundColor(backgroundColor);
        chart.getStyler().setChartBackgroundColor(backgroundColor);

        // Series
        XYSeries series = chart.addSeries("members", date, values);
        series.setSmooth(true);
        series.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);

        //SeriesColor
        series.setLineColor(lineColor);
        series.setMarkerColor(lineColor);
        series.setFillColor(fillColor);

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
