package com.prm.gsms.activities.report;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
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
import com.prm.gsms.activities.import_order.ImportOrderDetailsActivity;
import com.prm.gsms.activities.import_order.ImportOrderListActivity;
import com.prm.gsms.activities.login.DashboardActivity;
import com.prm.gsms.adapters.ImportOrderAdapter;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {
    private BarChart chSalesStatistics;
    private PieChart chProducts;
    private LineChart chRevenue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chSalesStatistics = findViewById(R.id.chSalesStatistics);
        chProducts = findViewById(R.id.chProducts);
        chRevenue = findViewById(R.id.chRevenue);

        createBarChart(chSalesStatistics);
        createPieChart(chProducts);
        createLineChart(chRevenue);
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
        data.add(new PieEntry(300,"Điện tử"));
        data.add(new PieEntry(200,"Đồ ăn cá nhân"));
        data.add(new PieEntry(600,"Đồ dùng ngọt"));
        data.add(new PieEntry(280,"Đồ dùng học tập"));

        // **********
        PieDataSet pieDataSet = new PieDataSet(data, "Categories");
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

    public void clickToBackToDashboardEmp(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("type", "employee");
        startActivity(intent);
    }
/*
    private static List<ImportOrder> importOrders = null;
    // ListView
    private ListView importOrderListView;
    // ListView Adapter
    private ImportOrderAdapter importOrderAdapter;

    private void loadImportOrderList(){
        importOrderListView = (ListView) findViewById(R.id.importOrderList);
        importOrderAdapter = new ImportOrderAdapter();

        TextView txtCountIO = (TextView) findViewById(R.id.txtCountIO);
        ProgressDialog progressDialog = GsmsUtils.showLoading(this,"Getting Import Orders...");
        try {
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "import-orders" ,
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            importOrders = ImportOrderService.getImportOrders(result);
                            txtCountIO.setText("Number of Import Orders: " + importOrders.size() + " orders");
                            importOrderAdapter.setImportOrderList(importOrders);
                            importOrderListView.setAdapter(importOrderAdapter);
                            importOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    ImportOrder importOrder = (ImportOrder) importOrderListView.getItemAtPosition(i);
                                    Intent intent = new Intent(ReportActivity.this, ImportOrderDetailsActivity.class);
                                    intent.putExtra("importOrder", importOrder);
                                    startActivity(intent);
                                }
                            });
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error){
                            progressDialog.dismiss();
                            Toast.makeText(ReportActivity.this, "An error has occured! Please check the log for more information...", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
        } catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(this, "An error has occured! Please check the log for more information...", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
*/
}