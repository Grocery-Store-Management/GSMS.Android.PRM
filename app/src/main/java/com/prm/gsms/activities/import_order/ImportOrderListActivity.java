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
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prm.gsms.R;

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

    private void loadImportOrderList(){
        importOderListView = (ListView) findViewById(R.id.importOrderList);
        importOrderAdapter = new ImportOrderAdapter();

        TextView txtCountIO = (TextView) findViewById(R.id.txtCountIO);

        try {


            RequestQueue queue = Volley.newRequestQueue(this);
            String apiUrl = getResources().getString(R.string.apiUrl) + "/import-orders";
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET,
                    apiUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Gson gson = GsmsUtils.createGson();
                            List<ImportOrder> importOrders = new ArrayList<>();
                            Type type = new TypeToken<ArrayList<ImportOrder>>(){}.getType();
                            importOrders = gson.fromJson(response, type);

                            txtCountIO.setText(importOrders.size() + "");
                            importOrderAdapter.setImportOrderList(importOrders);
                            importOderListView.setAdapter(importOrderAdapter);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            txtCountIO.setText("Failed!!");
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> headers = new HashMap<>();
                    headers.put("User-Agent", "GSMS-app");
                    headers.put("Authorization", "Bearer " + getResources().getString(R.string.bearer));

                    return headers;
                }
            };


            queue.add(stringRequest);

//            txtCountIO.setText("Count" + importOrders.size());
//            importOrderAdapter.setImportOrderList(importOrders);
//            importOderListView.setAdapter(importOrderAdapter);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}