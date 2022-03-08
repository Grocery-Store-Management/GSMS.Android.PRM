package com.prm.gsms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.prm.gsms.R;
import com.prm.gsms.dtos.ImportOrderDetail;

import org.w3c.dom.Text;

import java.util.List;

public class ImportOrderDetailsAdapter extends BaseAdapter {
    private List<ImportOrderDetail> importOrderDetailList;

    public void setImportOrderDetailList(List<ImportOrderDetail> importOrderDetails)
    {
        importOrderDetailList = importOrderDetails;
    }
    @Override
    public int getCount() {
        return importOrderDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return importOrderDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.import_order_details_list_item, viewGroup, false);
        }

        TextView txtCountIOD = view.findViewById(R.id.txtCountIOD);
        TextView txtIODProductName = view.findViewById(R.id.txtIODProductName);
        TextView txtIODPrice = view.findViewById(R.id.txtIODPrice);
        TextView txtIODQuantity = view.findViewById(R.id.txtIODQuantity);
        TextView txtIODDistributor = view.findViewById(R.id.txtIODDistributor);

        ImportOrderDetail importOrderDetail = importOrderDetailList.get(i);

        txtCountIOD.setText((i + 1) + "");
        txtIODProductName.setText(importOrderDetail.getName());
        txtIODPrice.setText("Price: " + importOrderDetail.getPrice() + "");
        txtIODQuantity.setText("Quantity: " + importOrderDetail.getQuantity() + "");
        txtIODDistributor.setText("Distributor: " +
                (importOrderDetail.getDistributor() != null
                ? importOrderDetail.getDistributor()
                : ""));
        return view;
    }
}
