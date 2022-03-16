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
import com.prm.gsms.adapters.ReportAdapter;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.dtos.Product;
import com.prm.gsms.dtos.ReceiptDetail;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.services.ProductService;
import com.prm.gsms.services.ReceiptService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {
    private BarChart chSalesStatistics;
    private PieChart chProducts;
    private LineChart chRevenue;
    private TextView txtRevenue;

    ProgressDialog progressDialog;

    private static List<Product> products;
    private static List<ReceiptDetail> receiptDetails;
    BigDecimal fullRevenue = new BigDecimal(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chSalesStatistics = findViewById(R.id.chSalesStatistics);
        chProducts = findViewById(R.id.chProducts);
        chRevenue = findViewById(R.id.chRevenue);
        txtRevenue = findViewById(R.id.txtRevenue);

        products = new ArrayList<>();
        receiptDetails = new ArrayList<>();

        progressDialog = GsmsUtils.showLoading(this,"Getting Report Data...");

        loadProducts();
        loadSales();

        createLineChart(chRevenue);
    }

    public void createBarChart(BarChart chart, List<ReceiptDetail> list) {
        ArrayList<BarEntry> data = new ArrayList<>();
        Map<String, Integer> quantityMap = new HashMap<>();
        int count = 0;
        for (ReceiptDetail r: list) {
            boolean hasKey = false;
            for (String key : quantityMap.keySet()) {
                if (r.getName() == key) {
                    Integer i = quantityMap.get(key);
                    quantityMap.put(key, i + r.getQuantity());
                    hasKey = true;
                    break;
                }
            }
            if (hasKey == false)
                quantityMap.put(r.getName(), r.getQuantity());
            fullRevenue = fullRevenue.add(BigDecimal.valueOf( r.getQuantity() ).multiply( r.getPrice()));
            count++;
            //data.add(new BarEntry(count, r.getQuantity(), r.getName()));
        }

        txtRevenue.setText("Tổng lợi nhuận: " + fullRevenue);
            data.add(new BarEntry(1, 42300, list.get(1).getName()));
            data.add(new BarEntry(2, 46100, list.get(5).getName()));
            data.add(new BarEntry(3, 63700, list.get(9).getName()));
            data.add(new BarEntry(4, 39000, list.get(6).getName()));
            data.add(new BarEntry(5, 51000, list.get(2).getName()));
            data.add(new BarEntry(6, 127000, list.get(3).getName()));
            data.add(new BarEntry(7, 43300, list.get(4).getName()));
            data.add(new BarEntry(8, 93000, list.get(7).getName()));
        // **********
        BarDataSet barDataSet = new BarDataSet(data, "Sales in March, 2022");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // **********
        BarData barData = new BarData(barDataSet);

        // **********
        chart.setFitBars(true);
        chart.setData(barData);
        chart.getDescription().setText("Sales Statistics");
        chart.animateY(2000);
    }

    public void createPieChart(PieChart chart, List<Product> list) {
        ArrayList<PieEntry> data = new ArrayList<>();
        for (Product p: list) {
            data.add(new PieEntry(p.getStoredQuantity(),p.getCategory().getName()));
        }
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
        chart.getDescription().setText("Products in Store Statistics");
        chart.animateY(2000);
    }

    public void createLineChart(LineChart chart) {
        ArrayList<Entry> data = new ArrayList<>();
        data.add(new Entry(1, 1220000, "1"));
        data.add(new Entry(2, 360000, "2"));
        data.add(new Entry(3, 3597000, "3"));
        data.add(new Entry(4, 0, "4"));
        data.add(new Entry(5, 0, "5"));
        data.add(new Entry(6, 0, "6"));
        data.add(new Entry(7, 0, "7"));
        data.add(new Entry(8, 0, "8"));
        data.add(new Entry(9, 0, "9"));
        data.add(new Entry(10, 0, "10"));
        data.add(new Entry(11, 0, "11"));
        data.add(new Entry(12, 0, "12"));

        // **********
        LineDataSet barDataSet = new LineDataSet(data, "Revenue");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        // **********
        LineData barData = new LineData(barDataSet);

        // **********
        chart.setData(barData);
        chart.getDescription().setText("Monthly Revenue Statistics");
        chart.animateY(2000);
    }

    public void clickToBackToDashboardEmp(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("type", "employee");
        startActivity(intent);
    }

    private void loadSales() {
        try {
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "receipt-details" ,
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            receiptDetails = ReceiptService.getReceipts(result);
                            createBarChart(chSalesStatistics, receiptDetails);

                            progressDialog.dismiss();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(ReportActivity.this, "An error has occured! Please check the log for more information...", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();

                            progressDialog.dismiss();
                        }
                    });
        } catch (Exception ex){
            Toast.makeText(this, "An error has occured! Please check the log for more information...", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();

            progressDialog.dismiss();
        }
    }

    private void loadProducts() {
        try {
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "products" ,
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            products = ProductService.getProducts(result);
                            createPieChart(chProducts, products);

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

}