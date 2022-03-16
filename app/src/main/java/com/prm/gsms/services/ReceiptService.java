package com.prm.gsms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.ReceiptDetail;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ReceiptService {
    public static List<ReceiptDetail> getReceipts(String productJson) {
        Gson gson = GsmsUtils.createGson();
        List<ReceiptDetail> receipts = new ArrayList<>();
        Type type = new TypeToken<ArrayList<ReceiptDetail>>(){}.getType();
        receipts = gson.fromJson(productJson, type);
        return receipts;
    }
}
