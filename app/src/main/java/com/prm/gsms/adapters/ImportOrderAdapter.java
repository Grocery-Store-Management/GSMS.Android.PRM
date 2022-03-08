package com.prm.gsms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.dtos.ImportOrder;

import java.util.List;

public class ImportOrderAdapter extends BaseAdapter {
    private List<ImportOrder> importOrderList;

    public void setImportOrderList(List<ImportOrder> importOrderList) {
        this.importOrderList = importOrderList;
    }

    @Override
    public int getCount() {
        return importOrderList.size();
    }

    @Override
    public Object getItem(int i) {
        return importOrderList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.import_order_list_item, viewGroup, false);
        }

        TextView txtImportOrderName = view.findViewById(R.id.txtImportOrderName);
        TextView txtImportOrderStore = view.findViewById(R.id.txtImportOrderStore);
        TextView txtImportOrderDate = view.findViewById(R.id.txtImportOrderDate);

        ImportOrder importOrder = importOrderList.get(i);

        txtImportOrderName.setText(importOrder.getName());
        txtImportOrderStore.setText("Store: " + importOrder.getStore().getName());
        txtImportOrderDate.setText("Import Date: " + importOrder.getCreatedDate());
        return view;
    }
}
