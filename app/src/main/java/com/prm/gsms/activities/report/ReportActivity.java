package com.prm.gsms.activities.report;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.prm.gsms.R;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private BarChart chSalesStatistics;
    private PieChart chProducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        chSalesStatistics = findViewById(R.id.chSalesStatistics);


    }

    public void createBarChart(BarChart chart) {
        ArrayList<BarEntry> data = new ArrayList<>();
        data.add(new BarEntry(2014, 420));
        data.add(new BarEntry(2015, 460));
        data.add(new BarEntry(2016, 437));
        data.add(new BarEntry(2017, 390));
        data.add(new BarEntry(2018, 510));
        data.add(new BarEntry(2019, 670));
        data.add(new BarEntry(2020, 633));

        // **********
        BarDataSet barDataSet = new BarDataSet(data, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // **********
        BarData barData = new BarData(barDataSet);

        // **********
        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setText("Bar Chart");
        chart.animateY(2000);
    }

    public void createPieChart(PieChart chart) {
        ArrayList<PieEntry> data = new ArrayList<>();
        data.add(new PieEntry(300,"Electricity"));
        data.add(new PieEntry(200,"Drink"));
        data.add(new PieEntry(600,"Food"));
        data.add(new PieEntry(280,"J97"));
        data.add(new PieEntry(900,"Jack Nguyen"));

        // **********
        PieDataSet pieDataSet = new PieDataSet(data, "Visitors");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        // **********
        PieData pieData = new PieData(pieDataSet);

        // **********
//        chart.setCenterText("");
        chart.setData(pieData);
        chart.getDescription().setEnabled(true);
        chart.getDescription().setText("Pie Chart");
        chart.animateY(2000);
    }

    public void createLineChart(LineChart chart) {
        ArrayList<Entry> data = new ArrayList<>();
        data.add(new Entry(2014, 420));
        data.add(new Entry(2015, 460));
        data.add(new Entry(2016, 437));
        data.add(new Entry(2017, 390));
        data.add(new Entry(2018, 510));
        data.add(new Entry(2019, 670));
        data.add(new Entry(2020, 633));

        // **********
        LineDataSet barDataSet = new LineDataSet(data, "Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // **********
        LineData barData = new LineData(barDataSet);

        // **********
        chart.setData(barData);
        chart.getDescription().setText("Bar Chart");
        chart.animateY(2000);
    }
}