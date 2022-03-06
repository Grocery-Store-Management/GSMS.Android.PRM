package com.prm.gsms.activities.import_order;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.R;
import com.prm.gsms.adapters.ImportOrderAdapter;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prm.gsms.R;
import com.prm.gsms.utils.VolleyCallback;

public class ImportOrderListActivity extends AppCompatActivity {

    private ListView importOderListView;

    // ListView Adapter
    private ImportOrderAdapter importOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_order_list);

        loadImportOrderList();

    }
    private static List<ImportOrder> importOrders = null;

    private void loadImportOrderList(){
        importOderListView = (ListView) findViewById(R.id.importOrderList);
        importOrderAdapter = new ImportOrderAdapter();

        TextView txtCountIO = (TextView) findViewById(R.id.txtCountIO);
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            GsmsUtils.apiUtils(this, Request.Method.GET,  "import-orders" , new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    importOrders = ImportOrderService.getImportOrders(result);
                    txtCountIO.setText(importOrders.size() + "");
                    importOrderAdapter.setImportOrderList(importOrders);
                    importOderListView.setAdapter(importOrderAdapter);
                }
            });

//            txtCountIO.setText("Count" + importOrders.size());
//            importOrderAdapter.setImportOrderList(importOrders);
//            importOderListView.setAdapter(importOrderAdapter);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}