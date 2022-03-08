package com.prm.gsms.activities.import_order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.prm.gsms.R;
import com.prm.gsms.adapters.ImportOrderAdapter;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.utils.GsmsUtils;

import java.util.List;

import com.prm.gsms.utils.VolleyCallback;

public class ImportOrderListActivity extends AppCompatActivity {

    private ListView importOrderListView;

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
        importOrderListView = (ListView) findViewById(R.id.importOrderList);
        importOrderAdapter = new ImportOrderAdapter();

        TextView txtCountIO = (TextView) findViewById(R.id.txtCountIO);
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
                            Intent intent = new Intent(ImportOrderListActivity.this, ImportOrderDetailsActivity.class);
                            intent.putExtra("importOrder", importOrder);
                            startActivity(intent);
                        }
                    });
                }

            });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}