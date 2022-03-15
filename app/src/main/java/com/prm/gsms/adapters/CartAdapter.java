package com.prm.gsms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.dtos.ImportOrderCartItem;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private List<ImportOrderCartItem> cartItems;

    public void setCartItems(List<ImportOrderCartItem> items){
        cartItems = items;
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int i) {
        return cartItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.import_order_details_list_item, viewGroup, false);
        }

        TextView txtCountIOD = view.findViewById(R.id.txtCountIOD);
        TextView txtIODProductName = view.findViewById(R.id.txtIODProductName);
        TextView txtIODPrice = view.findViewById(R.id.txtIODPrice);
        TextView txtIODQuantity = view.findViewById(R.id.txtIODQuantity);
        TextView txtIODDistributor = view.findViewById(R.id.txtIODDistributor);

        ImportOrderCartItem cartItem = cartItems.get(i);

        txtCountIOD.setText((i + 1) + "");
        txtIODProductName.setText(cartItem.getName());
        txtIODPrice.setText("Price: " + cartItem.getPrice());
        txtIODQuantity.setText("Quantity: " + cartItem.getOrderQuantity());
        txtIODDistributor.setText("Distributor: " +
                (cartItem.getDistributor() != null
                ? cartItem.getDistributor()
                : ""));
        return view;
    }
}
