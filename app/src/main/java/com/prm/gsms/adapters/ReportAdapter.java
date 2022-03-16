package com.prm.gsms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.dtos.ImportOrderCartItem;
import com.prm.gsms.dtos.Product;

import java.util.List;

public class ReportAdapter extends BaseAdapter {
    private List<Product> productList;

    @Override
    public int getCount() { return productList.size(); }

    @Override
    public Object getItem(int i) { return productList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.activity_report, viewGroup, false);
        }

        TextView txtCountIOD = view.findViewById(R.id.txtCountIOD);
        TextView txtIODProductName = view.findViewById(R.id.txtIODProductName);
        TextView txtIODPrice = view.findViewById(R.id.txtIODPrice);

        Product cartItem = productList.get(i);

        txtCountIOD.setText((i + 1) + "");
        txtIODProductName.setText(cartItem.getName());
        txtIODPrice.setText("Price: " + cartItem.getPrice());

        return view;
    }
}
