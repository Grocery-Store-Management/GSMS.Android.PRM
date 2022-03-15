package com.prm.gsms.activities.import_order.create;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.prm.gsms.R;
import com.prm.gsms.activities.import_order.ImportOrderListActivity;
import com.prm.gsms.adapters.CartAdapter;
import com.prm.gsms.dtos.Employee;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.dtos.ImportOrderCartItem;
import com.prm.gsms.dtos.ImportOrderDetail;
import com.prm.gsms.services.EmployeeService;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private ListView cartItemList;
    private CartAdapter cartAdapter;
    private List<ImportOrderCartItem> cartItems = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        loadCart();
    }

    private void loadCart() {
        TextView txtCartTotalItem = findViewById(R.id.txtCartTotalItem);
        try {
            File file = getBaseContext().getFileStreamPath(ImportOrderService.CART_FILE_NAME);
            if (!file.exists()) {
                ImportOrderService.saveToCart(this, new ArrayList<>());
                cartItems = new ArrayList<>();
                txtCartTotalItem.setText("No item in cart!");
            } else {
                cartItems = ImportOrderService.getCart(this);
                if (cartItems != null && cartItems.size() > 0) {
                    txtCartTotalItem.setText("Cart items: " + cartItems.size());
                    cartAdapter = new CartAdapter();
                    cartItemList = (ListView) findViewById(R.id.cartItemList);
                    cartAdapter.setCartItems(cartItems);
                    cartItemList.setAdapter(cartAdapter);
                    cartItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            ImportOrderCartItem item = (ImportOrderCartItem) cartItemList.getItemAtPosition(i);
                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Delete item from order");
                            builder.setMessage("Are you sure that you want to remove this product '" +
                                    item.getName() + "' from the order?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        cartItems = ImportOrderService.removeFromCart(cartItems, item.getId(), item.getDistributor());
                                        ImportOrderService.saveToCart(CartActivity.this, cartItems);
                                        Toast.makeText(CartActivity.this, "Remove from cart successfully!", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();;
                                }
                            });
                            AlertDialog confirmRemove = builder.create();
                            confirmRemove.show();
                        }
                    });
                } else {
                    cartItems = new ArrayList<>();
                    txtCartTotalItem.setText("No item in cart!");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void clickToAddMore(View view) {
        Intent intent = new Intent(this, CreateImportOrderActivity.class);
        startActivity(intent);
    }

    private ProgressDialog progressDialog = null;

    public void clickToPlaceOrder(View view) {
        if (cartItems.size() == 0) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("No item in cart")
                    .setMessage("Add at least one item to place the order!!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .show();
            return;
        }

        progressDialog = GsmsUtils.showLoading(this,"Placing order... Please wait");
        try {
            String employeeId = GsmsUtils.getCurrentUserId(this);
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "employees/" + employeeId,
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Employee currentEmployee = EmployeeService.getEmployee(result);

                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("Import Order Name ");

                            View viewInflated = LayoutInflater.from(CartActivity.this)
                                    .inflate(R.layout.import_order_create_name, null, false);
                            builder.setView(viewInflated);

                            builder.setPositiveButton("Create Import Order", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        EditText edtCreateIOName = viewInflated.findViewById(R.id.edtCreateIOName);
                                        placeOrder(currentEmployee.getStoreId(),
                                                edtCreateIOName.getText().toString());
                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                    }
                                }
                            });

                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    progressDialog.dismiss();
                                }
                            });
                            builder.show();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(CartActivity.this, "An error has occured! Please check the log for more information", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void placeOrder(String storeId, String importOrderName) {
        try {
            List<ImportOrderDetail> importOrderDetails = new ArrayList<>();
            for (ImportOrderCartItem cartItem :
                    cartItems) {
                importOrderDetails.add(new ImportOrderDetail(
                        "",
                        "",
                        cartItem.getName(),
                        cartItem.getDistributor(),
                        cartItem.getId(),
                        cartItem.getOrderQuantity(),
                        false,
                        cartItem.getPrice()
                ));
            }
            ImportOrder importOrder = new ImportOrder(
                    "",
                    importOrderName,
                    storeId,
                    false,
                    null,
                    importOrderDetails
            );
            GsmsUtils.apiUtils(this,
                    Request.Method.POST,
                    "import-orders",
                    ImportOrderService.getJsonString(importOrder),
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            progressDialog.dismiss();
                            Toast.makeText(CartActivity.this,
                                    "Create Import Order successfully!!", Toast.LENGTH_SHORT).show();

                            try {
                                cartItems = new ArrayList<>();
                                ImportOrderService.saveToCart(CartActivity.this, cartItems);
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                            Intent intent = new Intent(CartActivity.this, ImportOrderListActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(CartActivity.this, "An error has occured! Please check the log for more information", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
        } catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(CartActivity.this, "An error has occured! Please check the log for more information", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }
}