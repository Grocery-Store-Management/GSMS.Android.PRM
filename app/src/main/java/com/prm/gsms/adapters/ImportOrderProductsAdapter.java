package com.prm.gsms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.dtos.Product;

import java.util.List;

public class ImportOrderProductsAdapter extends BaseAdapter {
    private List<Product> productList;

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.import_order_product_list_item, viewGroup, false);
        }

        TextView txtProductName = view.findViewById(R.id.txtProductName);
        TextView txtProductPrice = view.findViewById(R.id.txtProductPrice);
        TextView txtProductCategory = view.findViewById(R.id.txtProductCategory);

        Product product = productList.get(i);

        txtProductName.setText(product.getName());
        txtProductPrice.setText("Price: " + product.getPrice() + "");
        txtProductCategory.setText("Category: " + product.getCategory().getName());
        return view;
    }
}
