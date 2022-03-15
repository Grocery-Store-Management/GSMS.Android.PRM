package com.prm.gsms.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm.gsms.dtos.Product;
import com.prm.gsms.utils.GsmsUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public static List<Product> getProducts(String productJson) {
        Gson gson = GsmsUtils.createGson();
        List<Product> products = new ArrayList<>();
        Type type = new TypeToken<ArrayList<Product>>(){}.getType();
        products = gson.fromJson(productJson, type);
        return products;
    }
}
