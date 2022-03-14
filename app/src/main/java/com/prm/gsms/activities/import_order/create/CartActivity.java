package com.prm.gsms.activities.import_order.create;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.adapters.CartAdapter;
import com.prm.gsms.dtos.ImportOrderCartItem;
import com.prm.gsms.services.ImportOrderService;

import java.io.File;
import java.util.ArrayList;
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
    }

    public void clickToPlaceOrder(View view) {
    }
}