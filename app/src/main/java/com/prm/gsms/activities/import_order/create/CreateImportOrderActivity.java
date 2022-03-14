package com.prm.gsms.activities.import_order.create;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.prm.gsms.activities.login.DashboardActivity;
import com.prm.gsms.adapters.ImportOrderProductsAdapter;
import com.prm.gsms.dtos.ImportOrderCartItem;
import com.prm.gsms.dtos.Product;
import com.prm.gsms.services.ImportOrderService;
import com.prm.gsms.services.ProductService;
import com.prm.gsms.utils.GsmsUtils;
import com.prm.gsms.utils.VolleyCallback;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CreateImportOrderActivity extends AppCompatActivity {

    private ListView productListView;

    private ImportOrderProductsAdapter productsAdapter;

    private List<ImportOrderCartItem> cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_import_order);

        loadCart();
        loadProductList();
    }

    private void loadCart() {
        TextView txtCountCart = findViewById(R.id.txtCountCart);
        try {
            File file = getBaseContext().getFileStreamPath(ImportOrderService.CART_FILE_NAME);
            if (!file.exists()) {
                ImportOrderService.saveToCart(this, new ArrayList<>());
                cart = new ArrayList<>();
                txtCountCart.setText("No item in cart!");
            } else {
                cart = ImportOrderService.getCart(this);
                if (cart != null && cart.size() > 0) {
                    txtCountCart.setText("Cart items: " + cart.size());
                } else {
                    cart = new ArrayList<>();
                    txtCountCart.setText("No item in cart!");
                }
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private List<Product> products = null;

    private void loadProductList() {
        productListView = (ListView) findViewById(R.id.productIOList);
        productsAdapter = new ImportOrderProductsAdapter();

        ProgressDialog progressDialog = GsmsUtils.showLoading(this, "Getting product list...");
        try {
            GsmsUtils.apiUtils(this,
                    Request.Method.GET,
                    "products",
                    "",
                    new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            products = ProductService.getProducts(result);
                            productsAdapter.setProductList(products);
                            productListView.setAdapter(productsAdapter);
                            productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Product product = (Product) productListView.getItemAtPosition(i);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateImportOrderActivity.this);
                                    builder.setTitle("Add Product to Import Order");

                                    View viewInflated = LayoutInflater.from(CreateImportOrderActivity.this).inflate(R.layout.import_order_product_add_item, (ViewGroup) view, false);
                                    TextView txtAddProductName = viewInflated.findViewById(R.id.txtAddProductName);
                                    TextView txtAddProductPrice = viewInflated.findViewById(R.id.txtAddProductPrice);
                                    txtAddProductName.setText(product.getName());
                                    txtAddProductPrice.setText("Price: " + product.getPrice());
                                    builder.setView(viewInflated);

                                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            try {
                                                EditText edtAddProductQuantity = viewInflated.findViewById(R.id.edtAddProductQuantity);
                                                EditText edtAddProductDistributor = viewInflated.findViewById(R.id.edtAddProductDistributor);
                                                ImportOrderCartItem item = new ImportOrderCartItem(
                                                        product,
                                                        Integer.parseInt(edtAddProductQuantity.getText().toString()),
                                                        edtAddProductDistributor.getText().toString());
                                                cart.add(item);
                                                ImportOrderService.saveToCart(CreateImportOrderActivity.this, cart);
                                                dialogInterface.dismiss();
                                                Toast.makeText(CreateImportOrderActivity.this, "Add to Import Order successfully", Toast.LENGTH_SHORT).show();
                                            } catch (Exception ex){
                                                ex.printStackTrace();
                                            }
                                        }
                                    });

                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });

                                    builder.show();
                                }
                            });
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(CreateImportOrderActivity.this, "An error has occured! Please check the log for more information", Toast.LENGTH_SHORT).show();
                            error.printStackTrace();
                        }
                    });
        } catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(this, "An error has occured! Please check the log for more information", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    public void clickToBackToDashboardIO(View view) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("type", "employee");
        startActivity(intent);
    }

    public void clickToViewCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }
}