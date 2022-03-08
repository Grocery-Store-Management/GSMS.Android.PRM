package com.prm.gsms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.ImportOrderDetail;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImportOrderDetailsService {
    public static List<ImportOrderDetail> getImportOrderDetails(String importOrderDetailsJson) {
        Gson gson = GsmsUtils.createGson();
        List<ImportOrderDetail> importOrderDetails = new ArrayList<>();
        Type type = new TypeToken<ArrayList<ImportOrderDetail>>(){}.getType();
        importOrderDetails = gson.fromJson(importOrderDetailsJson, type);
        return importOrderDetails;
    }
}
