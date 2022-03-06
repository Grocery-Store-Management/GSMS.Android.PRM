package com.prm.gsms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.ImportOrder;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportOrderService {
    public static List<ImportOrder> getImportOrders(String importOrderJson){
        Gson gson = GsmsUtils.createGson();
        List<ImportOrder> importOrders = new ArrayList<>();
        Type type = new TypeToken<ArrayList<ImportOrder>>(){}.getType();
        importOrders = gson.fromJson(importOrderJson, type);
        return importOrders;
    }
}
