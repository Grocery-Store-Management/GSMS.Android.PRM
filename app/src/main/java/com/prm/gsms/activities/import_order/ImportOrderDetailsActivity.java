package com.prm.gsms.activities.import_order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.prm.gsms.R;
import com.prm.gsms.adapters.ImportOrderDetailsAdapter;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.dtos.ImportOrderDetail;
import com.prm.gsms.services.ImportOrderDetailsService;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import java.util.List;

public class ImportOrderDetailsActivity extends AppCompatActivity {

    private ListView importOrderDetailsListView;
    private ImportOrderDetailsAdapter importOrderDetailsAdapter;
    private static List<ImportOrderDetail> importOrderDetails = null;
    private ImportOrder importOrder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_order_details);

        Intent intent = this.getIntent();
        importOrder = (ImportOrder) intent.getSerializableExtra("importOrder");

        TextView txtIODName = findViewById(R.id.txtIODName);
        txtIODName.setText(importOrder.getName());

        loadImportOrderDetailsList(importOrder.getId());
    }

    private void loadImportOrderDetailsList(String orderId) {
        importOrderDetailsListView = (ListView) findViewById(R.id.importOrderDetailsList);
        importOrderDetailsAdapter = new ImportOrderDetailsAdapter();

        try {
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "import-order-details?orderId=" + orderId,
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            importOrderDetails = ImportOrderDetailsService.getImportOrderDetails(result);
                            importOrderDetailsAdapter.setImportOrderDetailList(importOrderDetails);
                            importOrderDetailsListView.setAdapter(importOrderDetailsAdapter);

                        }
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TODO
                        }
                    });
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void clickToCancelIO(View view) {
        try {
            importOrder.setDeleted(true);
             GsmsUtils.apiUtils(this,
                     Request.Method.PUT,
                     "import-orders/" + importOrder.getId(),
                     ImportOrderService.getJsonString(importOrder),
                     new VolleyCallback() {
                         @Override
                         public void onSuccess(String result) {
                             Toast.makeText(ImportOrderDetailsActivity.this,
                                     "Cancel Order successfully!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ImportOrderDetailsActivity.this, ImportOrderListActivity.class);
                            startActivity(intent);
                         }
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             //TODO
                         }
                     }
             );
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void clickToBackToListIO(View view) {
        Intent intent = new Intent(this, ImportOrderListActivity.class);
        startActivity(intent);
    }
}